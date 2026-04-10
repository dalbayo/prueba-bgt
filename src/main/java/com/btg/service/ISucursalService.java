package com.btg.service;

import com.btg.model.Sucursal;
import java.util.List;

public interface ISucursalService {
    void save(Sucursal entity);
    Sucursal findById(Integer id);
    List<Sucursal> findAll();
    void delete(Integer id);
}
