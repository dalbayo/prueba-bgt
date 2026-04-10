package com.btg.service.impl;

import com.btg.model.Producto;
import com.btg.repository.ProductoRepository;
import com.btg.service.IProductoService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public void save(Producto entity) {
        productoRepository.save(entity);
    }

    @Override
    public Producto findById(Integer id) {
        // Nota: Ajustar tipo de dato si la entidad usa llaves compuestas String
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        productoRepository.delete(id);
    }
}
