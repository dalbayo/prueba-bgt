package com.btg.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;
import java.util.Date;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @DynamoDbBean
public class Usuario {
    private Integer id;
    private String username;
    private String password;
    private Boolean activo;
    private String token;
    private Instant expirydate;

    @DynamoDbPartitionKey
    public Integer getId() { return id; }
}
