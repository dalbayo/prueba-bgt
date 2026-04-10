package com.btg.service;

import com.btg.model.Inscripcion;
import java.util.List;

public interface IInscripcionService {
    void suscribir(Integer idCliente, Inscripcion inscripcion, String preferenciaNotificacion );
    Inscripcion cancelar(Integer idCliente, String idInscripcion, String preferenciaNotificacion);
    List<Inscripcion> obtenerHistorico(Integer idCliente);
}
