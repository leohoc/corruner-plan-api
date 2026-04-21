output "ecr_repository_url" {
  description = "ECR repository URL — use this in GitHub Actions to build and push images"
  value       = module.ecr.repository_url
}

output "github_actions_role_arn" {
  description = "IAM role ARN — set as AWS_ROLE_ARN_QA secret in GitHub Actions"
  value       = module.iam_github_oidc.role_arn
}

output "alb_dns_name" {
  description = "ALB DNS name — the public URL of the QA application"
  value       = module.app_infra.alb_dns_name
}

output "ecs_cluster_name" {
  description = "ECS cluster name — set as ECS_CLUSTER_QA variable in GitHub Actions"
  value       = module.app_infra.ecs_cluster_name
}

output "ecs_service_name" {
  description = "ECS service name — set as ECS_SERVICE variable in GitHub Actions"
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

output "ecr_registry_id" {
  description = "ECR registry ID (AWS account ID) — needed for cross-account ECR login in PROD"
  value       = module.ecr.registry_id
}
