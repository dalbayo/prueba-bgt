############################################
# S3 BUCKET PARA ARTEFACTOS (JAR)
############################################
resource "aws_s3_bucket" "app_bucket" {
  bucket = var.bucket_name

  tags = {
    Name = var.bucket_name
    Env  = var.environment
  }
}

############################################
# SUBIR JAR AL BUCKET
############################################
resource "aws_s3_object" "app_jar" {
  bucket = aws_s3_bucket.app_bucket.id
  key    = "app/btg-backend-0.0.1-SNAPSHOT.jar"
  source = var.jar_path

  etag = filemd5(var.jar_path)
}