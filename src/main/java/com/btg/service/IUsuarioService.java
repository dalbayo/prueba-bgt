package com.btg.service;

import com.btg.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    void save(Usuario entity);
    Usuario findById(Integer id);
    List<Usuario> findAll();
    void delete(Integer id);
    Optional<Usuario> findByToken(String token);
}
