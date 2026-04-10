package com.btg.service.impl;

import com.btg.model.Cliente;
import com.btg.model.Inscripcion;
import com.btg.model.Producto;
import com.btg.repository.ClienteRepository;
import com.btg.service.IClienteService;
import com.btg.service.INotificationService;
import com.btg.service.IProductoService;
import com.btg.util.EstadoInscripcion;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository; 

    @Override
    public void save(Cliente entity) {
        clienteRepository.save(entity);
    }

    @Override
    public Cliente findById(Integer id) {
        // Nota: Ajustar tipo de dato si la entidad usa llaves compuestas String
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        clienteRepository.delete(id);
    }

	 
}
