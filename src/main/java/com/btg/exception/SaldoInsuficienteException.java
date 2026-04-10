package com.btg.exception;

public class SaldoInsuficienteException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3481751098755091996L;

	public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
