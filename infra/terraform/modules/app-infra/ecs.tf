resource "aws_cloudwatch_log_group" "this" {
  name              = "/ecs/${local.name_prefix}"
  retention_in_days = 14

  tags = { Name = "${local.name_prefix}-logs" }
}

# Task Execution Role — used by the ECS agent to pull the image and write logs.
resource "aws_iam_role" "ecs_task_execution" {
  name = "${local.name_prefix}-ecs-exec-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect    = "Allow"
      Principal = { Service = "ecs-tasks.amazonaws.com" }
      Action    = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution" {
  role       = aws_iam_role.ecs_task_execution.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

# Grants the task execution role permission to read DB credentials from Secrets Manager.
resource "aws_iam_role_policy" "secrets_read" {
  name = "secrets-read"
  role = aws_iam_role.ecs_task_execution.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect   = "Allow"
      Action   = ["secretsmanager:GetSecretValue"]
      Resource = aws_secretsmanager_secret.db.arn
    }]
  })
}

# Cross-account ECR pull — only wired up for PROD (when ecr_cross_account_repository_arn is set).
# Required because AmazonECSTaskExecutionRolePolicy alone doesn't grant cross-account ECR access.
resource "aws_iam_role_policy" "ecr_cross_account_pull" {
  count = var.ecr_cross_account_repository_arn != "" ? 1 : 0
  name  = "ecr-cross-account-pull"
  role  = aws_iam_role.ecs_task_execution.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Action = [
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage"
      ]
      Resource = var.ecr_cross_account_repository_arn
    }]
  })
}

# Task Role — the role the application itself assumes at runtime. Kept minimal for now.
resource "aws_iam_role" "ecs_task" {
  name = "${local.name_prefix}-ecs-task-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect    = "Allow"
      Principal = { Service = "ecs-tasks.amazonaws.com" }
      Action    = "sts:AssumeRole"
    }]
  })
}

resource "aws_ecs_cluster" "this" {
  name = local.name_prefix

  setting {
    # Container Insights adds ~$0.50/GB of logs; disable until you need the dashboards.
    name  = "containerInsights"
    value = "disabled"
  }

  tags = { Name = local.name_prefix }
}

resource "aws_ecs_task_definition" "this" {
  family                   = local.name_prefix
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = var.task_cpu
  memory                   = var.task_memory
  execution_role_arn       = aws_iam_role.ecs_task_execution.arn
  task_role_arn            = aws_iam_role.ecs_task.arn

  container_definitions = jsonencode([{
    name      = "app"
    image     = var.container_image
    essential = true

    portMappings = [{
      containerPort = 8080
      protocol      = "tcp"
    }]

    environment = [
      { name = "SPRING_PROFILES_ACTIVE", value = "cloud" }
    ]

    # DB credentials are injected from Secrets Manager at container startup.
    # The ECS agent resolves each valueFrom reference before the container starts.
    secrets = [
      { name = "DB_USERNAME", valueFrom = "${aws_secretsmanager_secret.db.arn}:username::" },
      { name = "DB_PASSWORD", valueFrom = "${aws_secretsmanager_secret.db.arn}:password::" },
      { name = "DB_HOST",     valueFrom = "${aws_secretsmanager_secret.db.arn}:host::" },
      { name = "DB_PORT",     valueFrom = "${aws_secretsmanager_secret.db.arn}:port::" },
      { name = "DB_NAME",     valueFrom = "${aws_secretsmanager_secret.db.arn}:dbname::" }
    ]

    logConfiguration = {
      logDriver = "awslogs"
      options = {
        "awslogs-group"         = aws_cloudwatch_log_group.this.name
        "awslogs-region"        = "us-east-1"
        "awslogs-stream-prefix" = "app"
      }
    }
  }])

  # CI/CD manages the image tag by registering new task definition revisions.
  # Terraform must not override those updates on subsequent applies.
  lifecycle {
    ignore_changes = [container_definitions]
  }

  tags = { Name = local.name_prefix }
}

resource "aws_ecs_service" "this" {
  name            = "corunner-plan-api"
  cluster         = aws_ecs_cluster.this.id
  task_definition = aws_ecs_task_definition.this.arn
  desired_count   = var.ecs_desired_count
  launch_type     = "FARGATE"

  network_configuration {
    subnets          = aws_subnet.private[*].id
    security_groups  = [aws_security_group.ecs.id]
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.this.arn
    container_name   = "app"
    container_port   = 8080
  }

  # Both task_definition and desired_count are managed outside Terraform after first apply:
  # - task_definition: updated by deploy workflows via aws ecs register-task-definition
  # - desired_count:   updated by start/stop workflows via aws ecs update-service
  lifecycle {
    ignore_changes = [task_definition, desired_count]
  }

  depends_on = [aws_lb_listener.http]

  tags = { Name = "${local.name_prefix}-service" }
}
