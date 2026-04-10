package com.btg.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @DynamoDbBean
public class Inscripcion {
	private String id; // llave única, puede ser UUID
    private Integer idProducto;
    private Double monto;
    private String estado;
    private Instant fechaApertura;
    private Instant fechaCancelacion;
    private Instant createdAt;
    private Instant updatedAt;
	private String idCancelacion; // llave única, puede ser UUID

    // Constructor de conveniencia que genera UUID automáticamente
    public Inscripcion(Integer idProducto, Double monto, String estado, Instant fechaApertura, Instant fechaCancelacion, 
    		Instant createdAt, Instant updatedA) {
        this.id = UUID.randomUUID().toString();
        this.idProducto = idProducto;
        this.monto = monto;
        this.estado = estado;
        this.fechaApertura = fechaApertura;
        this.fechaCancelacion = fechaCancelacion;
        this.createdAt = createdAt;
        this.updatedAt = null;
        this.idCancelacion = null;
    }
}
