package com.btg.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.util.List;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @DynamoDbBean
public class Cliente {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String ciudad;
    private Double saldo;
    private String correo;
    private String telefono;
    private String envioNotificaicones;

    private List<Inscripcion> inscripciones;
    private List<Visitan> visitas;
    
    @DynamoDbAttribute("inscripciones")
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    

 
    
    @DynamoDbAttribute("visitas")
    public List<Visitan> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<Visitan> visitas) {
        this.visitas = visitas;
    }
    
    

    @DynamoDbPartitionKey
    public Integer getId() { return id; } 
}
