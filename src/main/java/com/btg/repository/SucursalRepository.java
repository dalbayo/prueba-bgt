package com.btg.repository;

import com.btg.model.Sucursal;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SucursalRepository {

    private final DynamoDbTable<Sucursal> table;

    public SucursalRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("Sucursal", TableSchema.fromBean(Sucursal.class));
    }

    public void save(Sucursal entity) {
        table.putItem(entity);
    }

    public Sucursal findById(Integer id) {
        return table.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public List<Sucursal> findAll() {
        return table.scan().items().stream().collect(Collectors.toList());
    }

    public void delete(Integer id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
