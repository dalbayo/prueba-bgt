package com.btg.exception;

import com.btg.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErrorMessageDto> handleSaldoInsuficiente(SaldoInsuficienteException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ErrorMessageDto> handleProductoNoEncontrado(ProductoNoEncontradoException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    // Manejador genérico para cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "Ocurrió un error inesperado en el servidor",
                request.getDescription(false));
        
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}