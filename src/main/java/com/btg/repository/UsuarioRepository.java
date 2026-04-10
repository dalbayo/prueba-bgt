package com.btg.repository;

import com.btg.model.Usuario;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UsuarioRepository {

    private final DynamoDbTable<Usuario> table;

    public UsuarioRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("Usuario", TableSchema.fromBean(Usuario.class));
    }

    public Optional<Usuario> findByToken(String token) {
        return table.scan().items().stream()
                .filter(u -> token.equals(u.getToken()))
                .findFirst();
    }

    public Optional<Usuario> loadUserByUsername(String username) {
        return table.scan().items().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public void save(Usuario entity) {
        table.putItem(entity);
    }

    public Usuario findById(Integer id) {
        return table.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public List<Usuario> findAll() {
        return table.scan().items().stream().collect(Collectors.toList());
    }

    public void delete(Integer id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
