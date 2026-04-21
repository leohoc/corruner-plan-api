terraform {
  required_version = ">= 1.7"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

module "iam_github_oidc" {
  source      = "../../modules/iam-github-oidc"
  github_repo = var.github_repo
  environment = "prod"
  # No ECR here — PROD pulls images from the QA account's ECR via cross-account policy
}

module "app_infra" {
  source = "../../modules/app-infra"

  environment = "prod"

  # PROD pulls from QA ECR cross-account. The task execution role needs explicit pull
  # permissions in addition to the repository-side cross-account policy.
  ecr_cross_account_repository_arn = var.ecr_repository_arn

  db_instance_class   = var.db_instance_class
  ecs_desired_count   = var.ecs_desired_count
  deletion_protection = true
  task_cpu            = 1024
  task_memory         = 2048
}
