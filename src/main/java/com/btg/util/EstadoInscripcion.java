package com.btg.util; 

import lombok.Getter;

@Getter
public enum EstadoInscripcion {
    ACTIVA("ACTIVA"),
    INACTIVA("INACTIVA");

    private final String valor;

    EstadoInscripcion(String valor) {
        this.valor = valor;
    }
}