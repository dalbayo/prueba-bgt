package com.btg.service;

import com.btg.model.Producto;
import java.util.List;

public interface IProductoService {
    void save(Producto entity);
    Producto findById(Integer id);
    List<Producto> findAll();
    void delete(Integer id);
}
