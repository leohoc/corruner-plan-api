output "alb_dns_name" {
  description = "ALB DNS name — the public URL of the application"
  value       = aws_lb.this.dns_name
}

output "ecs_cluster_name" {
  description = "ECS cluster name — needed in CI/CD deploy and start/stop workflows"
  value       = aws_ecs_cluster.this.name
}

output "ecs_service_name" {
  description = "ECS service name — needed in CI/CD deploy and start/stop workflows"
  value       = aws_ecs_service.this.name
}

output "ecs_task_definition_family" {
  description = "ECS task definition family — needed in CI/CD to register new revisions"
  value       = aws_ecs_task_definition.this.family
}

output "db_secret_arn" {
  description = "Secrets Manager ARN containing DB credentials (username, password, host, port, dbname)"
  value       = aws_secretsmanager_secret.db.arn
}

output "log_group_name" {
  description = "CloudWatch log group name"
  value       = aws_cloudwatch_log_group.this.name
}

output "rds_identifier" {
  description = "RDS instance identifier — needed in start/stop workflows"
  value       = aws_db_instance.this.identifier
}
