package com.btg.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @DynamoDbBean
public class Visitan {
	private String id; // llave única
    private Integer idSucursal;
    private Instant fechaVisita;
    private String estado;
    private Instant fechaCreacion;
    private Instant fechaModificacion;

    // Constructor de conveniencia
    public Visitan(Integer idSucursal, Instant fechaVisita, String estado, Instant fechaCreacion, Instant fechaModificacion) {
        this.id = UUID.randomUUID().toString();
        this.idSucursal = idSucursal;
        this.fechaVisita = fechaVisita;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }
}
