package com.btg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.btg.model.Usuario;
import com.btg.repository.UsuarioRepository;
import com.btg.security.jwt.UserDetailsImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String id)
            throws UsernameNotFoundException {

    	/*
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));*/

    	Usuario sistema = usuarioRepository.findById(Integer.parseInt(id)) ;
        
        return new UserDetailsImpl(sistema);
    }
}
