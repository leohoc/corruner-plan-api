output "github_actions_role_arn" {
  description = "IAM role ARN — set as AWS_ROLE_ARN_PROD secret in GitHub Actions"
  value       = module.iam_github_oidc.role_arn
}

output "alb_dns_name" {
  description = "ALB DNS name — the public URL of the PROD application"
  value       = module.app_infra.alb_dns_name
}

output "ecs_cluster_name" {
  description = "ECS cluster name — set as ECS_CLUSTER_PROD variable in GitHub Actions"
  value       = module.app_infra.ecs_cluster_name
}

output "ecs_service_name" {
  description = "ECS service name — used by deploy and start/stop workflows"
  value       = module.app_infra.ecs_service_name
}

output "ecs_task_definition_family" {
  description = "ECS task definition family — used by deploy workflow to register new revisions"
  value       = module.app_infra.ecs_task_definition_family
}

output "rds_identifier" {
  description = "RDS instance identifier — used by start/stop workflows"
  value       = module.app_infra.rds_identifier
}
