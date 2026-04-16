terraform {
  backend "s3" {
    bucket         = "corunner-terraform-state-prod"
    key            = "phase1/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "corunner-terraform-locks"
    encrypt        = true
  }
}