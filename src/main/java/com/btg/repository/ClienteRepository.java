package com.btg.repository;

import com.btg.model.Cliente;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClienteRepository {

    private final DynamoDbTable<Cliente> table;

    public ClienteRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("Cliente", TableSchema.fromBean(Cliente.class));
    }

    public void save(Cliente entity) {
        table.putItem(entity);
    }

    public Cliente findById(Integer id) {
        return table.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public List<Cliente> findAll() {
        return table.scan().items().stream().collect(Collectors.toList());
    }

    public void delete(Integer id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
