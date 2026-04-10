package com.btg.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @DynamoDbBean
public class Producto {
    private Integer id;
    private String nombre;
    private String tipoProducto;
    private Double montoMinimo;
    private String estado;

    @DynamoDbPartitionKey
    public Integer getId() { return id; }
}
