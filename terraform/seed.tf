resource "null_resource" "seed_dynamodb" {

  depends_on = [
  	module.dynamodb_tables
  ]

  provisioner "local-exec" {
    interpreter = ["PowerShell", "-Command"]
    command     = "powershell -ExecutionPolicy Bypass -File seed.ps1"
  }
}