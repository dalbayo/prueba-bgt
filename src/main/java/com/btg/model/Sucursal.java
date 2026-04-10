package com.btg.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.util.List;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @DynamoDbBean
public class Sucursal {
    private Integer id;
    private String nombre;
    private String ciudad;
    
    private List<Disponibilidad> disponibilidades;

 
    
    @DynamoDbAttribute("disponibilidades")
    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }
    

 
    

    @DynamoDbPartitionKey
    public Integer getId() { return id; }
}
