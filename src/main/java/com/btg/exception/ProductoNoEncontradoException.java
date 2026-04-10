package com.btg.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4864387766980873594L;

	public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}