package com.btg.service;

import com.btg.model.Cliente;
import com.btg.model.Inscripcion;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IClienteService {
    void save(Cliente entity);
    Cliente findById(Integer id);
    List<Cliente> findAll();
    void delete(Integer id); 
}
