resource "random_password" "db" {
  length           = 32
  special          = true
  override_special = "!#$%&*()-_=+[]{}<>:?"
}

resource "aws_secretsmanager_secret" "db" {
  name = "${local.name_prefix}/db-credentials"

  # 0 = no recovery window, allows immediate deletion.
  # Acceptable in the dev phase; set to 7+ for production hardening.
  recovery_window_in_days = 0

  tags = { Name = "${local.name_prefix}-db-credentials" }
}

resource "aws_secretsmanager_secret_version" "db" {
  secret_id = aws_secretsmanager_secret.db.id

  secret_string = jsonencode({
    username = "corunner"
    password = random_password.db.result
    dbname   = var.db_name
    host     = aws_db_instance.this.address
    port     = "5432"
  })
}
