terraform {
  required_version = ">= 1.7"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
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