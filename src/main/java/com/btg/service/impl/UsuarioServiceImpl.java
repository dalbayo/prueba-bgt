package com.btg.service.impl;

import com.btg.model.Usuario;
import com.btg.repository.UsuarioRepository;
import com.btg.service.IUsuarioService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void save(Usuario entity) {
        usuarioRepository.save(entity);
    }

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.delete(id);
    }

    @Override
    public Optional<Usuario> findByToken(String token) {
        return usuarioRepository.findByToken(token);
    }
}
