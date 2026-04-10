module "dynamodb_tables" {
  source      = "./modules/dynamodb"
  environment = var.environment
}