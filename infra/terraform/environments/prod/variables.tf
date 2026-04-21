variable "github_repo" {
  description = "GitHub repository in 'owner/repo' format"
  type        = string
}

variable "ecr_repository_arn" {
  description = "ARN of the ECR repository in the QA account — grants PROD task execution role cross-account pull access"
  type        = string
}

variable "db_instance_class" {
  description = "RDS instance class"
  type        = string
  default     = "db.t4g.small"
}

variable "ecs_desired_count" {
  description = "Initial number of ECS tasks. Use 0 to start the environment paused."
  type        = number
  default     = 1
}
