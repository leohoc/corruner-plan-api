output "github_actions_role_arn" {
  description = "IAM role ARN to configure as AWS_ROLE_ARN in GitHub Actions"
  value       = module.iam_github_oidc.role_arn
}