variable "github_repo" {
  description = "GitHub repository in 'owner/repo' format"
  type        = string
}

variable "prod_account_id" {
  description = "PROD AWS account ID for cross-account ECR pull access. Fill in after the PROD account is created."
  type        = string
  default     = ""
}

variable "db_instance_class" {
  description = "RDS instance class"
  type        = string
  default     = "db.t4g.micro"
}

variable "ecs_desired_count" {
  description = "Initial number of ECS tasks. Use 0 to start the environment paused."
  type        = number
  default     = 1
}
