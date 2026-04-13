############################################
# IAM ROLE PARA EC2
############################################
resource "aws_iam_role" "ec2_role" {
  name = "ec2-dynamodb-s3-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = {
        Service = "ec2.amazonaws.com"
      }
      Action = "sts:AssumeRole"
    }]
  })
}

############################################
# POLICY (DynamoDB + S3)
############################################
resource "aws_iam_role_policy" "app_policy" {
  name = "ec2-app-policy"
  role = aws_iam_role.ec2_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [

      # DynamoDB FULL ACCESS (puedes restringir luego)
      {
        Effect = "Allow"
        Action = [
          "dynamodb:*"
        ]
        Resource = "*"
      },

      # S3 - SOLO LECTURA DEL BUCKET DE ARTEFACTOS
      {
        Effect = "Allow"
        Action = [
          "s3:GetObject"
        ]
        Resource = "arn:aws:s3:::btg-app-artifacts-dev/*"
      },

      # (IMPORTANTE) Permite listar bucket
      {
        Effect = "Allow"
        Action = [
          "s3:ListBucket"
        ]
        Resource = "arn:aws:s3:::btg-app-artifacts-dev"
      }
    ]
  })
}

############################################
# INSTANCE PROFILE
############################################
resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2-profile"
  role = aws_iam_role.ec2_role.name
}