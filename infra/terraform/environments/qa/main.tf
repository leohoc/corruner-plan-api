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

module "ecr" {
  source          = "../../modules/ecr"
  prod_account_id = var.prod_account_id
}

module "iam_github_oidc" {
  source             = "../../modules/iam-github-oidc"
  github_repo        = var.github_repo
  environment        = "qa"
  ecr_repository_arn = module.ecr.repository_arn
}

module "app_infra" {
  source = "../../modules/app-infra"

  environment         = "qa"
  db_instance_class   = var.db_instance_class
  ecs_desired_count   = var.ecs_desired_count
  deletion_protection = false
}
