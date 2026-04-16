variable "github_repo" {
  description = "GitHub repository in 'owner/repo' format"
  type        = string
}

variable "environment" {
  description = "Environment name (qa or prod)"
  type        = string
}

variable "ecr_repository_arn" {
  description = "ARN of the ECR repository to grant push access. Leave empty for PROD (uses cross-account pull instead)."
  type        = string
  default     = ""
}
