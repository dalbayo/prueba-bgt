package com.btg.repository;

import com.btg.model.Producto;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductoRepository {

    private final DynamoDbTable<Producto> table;

    public ProductoRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("Producto", TableSchema.fromBean(Producto.class));
    }

    public void save(Producto entity) {
        table.putItem(entity);
    }

    public Producto findById(Integer id) {
        return table.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public List<Producto> findAll() {
        return table.scan().items().stream().collect(Collectors.toList());
    }

    public void delete(Integer id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
