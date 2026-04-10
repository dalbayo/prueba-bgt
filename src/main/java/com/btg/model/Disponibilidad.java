package com.btg.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import java.util.UUID;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @DynamoDbBean
public class Disponibilidad {
	 private String id; // llave única interna
	    private Integer idProducto;
	    private Integer stock;

	    // Constructor de conveniencia que genera UUID automáticamente
	    public Disponibilidad(Integer idProducto, Integer stock) {
	        this.id = UUID.randomUUID().toString();
	        this.idProducto = idProducto;
	        this.stock = stock;
	    }
}
