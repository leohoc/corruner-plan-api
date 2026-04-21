variable "environment" {
  description = "Environment name (qa or prod)"
  type        = string

  validation {
    condition     = contains(["qa", "prod"], var.environment)
    error_message = "Must be 'qa' or 'prod'."
  }
}

variable "container_image" {
  description = "Full container image URI with tag. Defaults to a placeholder; CI/CD manages subsequent updates."
  type        = string
  default     = "public.ecr.aws/nginx/nginx:latest"
}

variable "ecr_cross_account_repository_arn" {
  description = "ARN of the cross-account ECR repository to grant explicit pull access. Required when the ECR lives in a different account (e.g., PROD pulling from QA ECR)."
  type        = string
  default     = ""
}

variable "ecs_desired_count" {
  description = "Initial number of running ECS tasks. Set to 0 to start the environment paused. After the first apply, use the start/stop workflows to manage this."
  type        = number
  default     = 1
}

variable "task_cpu" {
  description = "ECS task CPU units (1024 = 1 vCPU)"
  type        = number
  default     = 512
}

variable "task_memory" {
  description = "ECS task memory in MiB"
  type        = number
  default     = 1024
}

variable "db_instance_class" {
  description = "RDS instance class"
  type        = string
  default     = "db.t4g.micro"
}

variable "db_name" {
  description = "PostgreSQL database name"
  type        = string
  default     = "corunner_plan"
}

variable "deletion_protection" {
  description = "Enable RDS deletion protection. Set to true for PROD."
  type        = bool
  default     = false
}