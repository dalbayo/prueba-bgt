############################################
# TABLA: Cliente
############################################
resource "aws_dynamodb_table" "cliente" {
  name         = "Cliente-${var.environment}"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "N"
  }

  attribute {
    name = "correo"
    type = "S"
  }

  global_secondary_index {
    name            = "CorreoIndex"
    hash_key        = "correo"
    projection_type = "ALL"
  }

  point_in_time_recovery {
    enabled = true
  }

  server_side_encryption {
    enabled = true
  }

  tags = {
    Proyecto = "BTG-Pactual"
    Entorno  = var.environment
  }
}

############################################
# TABLA: Usuario
############################################
resource "aws_dynamodb_table" "usuario" {
  name         = "Usuario-${var.environment}"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "N"
  }

  attribute {
    name = "username"
    type = "S"
  }

  global_secondary_index {
    name            = "UsernameIndex"
    hash_key        = "username"
    projection_type = "ALL"
  }

  point_in_time_recovery {
    enabled = true
  }

  server_side_encryption {
    enabled = true
  }

  tags = {
    Proyecto = "BTG-Pactual"
    Entorno  = var.environment
  }
}

############################################
# TABLA: Sucursal
############################################
resource "aws_dynamodb_table" "sucursal" {
  name         = "Sucursal-${var.environment}"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "N"
  }

  server_side_encryption {
    enabled = true
  }

  tags = {
    Proyecto = "BTG-Pactual"
    Entorno  = var.environment
  }
}

############################################
# TABLA: Producto
############################################
resource "aws_dynamodb_table" "producto" {
  name         = "Producto-${var.environment}"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "N"
  }

  server_side_encryption {
    enabled = true
  }

  tags = {
    Proyecto = "BTG-Pactual"
    Entorno  = var.environment
  }
}
  