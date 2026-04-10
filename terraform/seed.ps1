$tables = Get-Content "seed.json" | ConvertFrom-Json

foreach ($table in $tables.PSObject.Properties) {
    $tableName = $table.Name
    $items = $table.Value

    Write-Host "AVISO: El item ya existe en $tableName"

    foreach ($item in $items) {

        $itemJson = $item | ConvertTo-Json -Compress

        aws dynamodb put-item `
            --table-name $tableName `
            --item $itemJson `
            --condition-expression "attribute_not_exists(id)" `
            2>$null

        if ($LASTEXITCODE -eq 0) {
            Write-Host "Insertado en $tableName"
        } else {
            Write-Host "Ya existe en $tableName"
        }
    }
}