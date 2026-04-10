output "cliente_table_name" {
  value = aws_dynamodb_table.cliente.name
}

output "usuario_table_name" {
  value = aws_dynamodb_table.usuario.name
}

output "sucursal_table_name" {
  value = aws_dynamodb_table.sucursal.name
}

output "producto_table_name" {
  value = aws_dynamodb_table.producto.name
}
  

# Ejemplo de salida de ARN para políticas de seguridad
output "cliente_table_arn" {
  value = aws_dynamodb_table.cliente.arn
}