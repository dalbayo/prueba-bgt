package com.btg.service.impl;

import com.btg.model.Sucursal;
import com.btg.repository.SucursalRepository;
import com.btg.service.ISucursalService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SucursalServiceImpl implements ISucursalService {

    private final SucursalRepository sucursalRepository;

    @Override
    public void save(Sucursal entity) {
        sucursalRepository.save(entity);
    }

    @Override
    public Sucursal findById(Integer id) {
        // Nota: Ajustar tipo de dato si la entidad usa llaves compuestas String
        return sucursalRepository.findById(id);
    }

    @Override
    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        sucursalRepository.delete(id);
    }
}
