variable "github_repo" {
  description = "GitHub repository in 'owner/repo' format"
  type        = string
}

variable "prod_account_id" {
  description = "PROD AWS account ID for cross-account ECR pull access. Fill in after the PROD account is created."
  type        = string
  default     = ""
}
