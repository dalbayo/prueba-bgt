package com.btg.service.impl;

import com.btg.exception.ProductoNoEncontradoException;
import com.btg.exception.SaldoInsuficienteException;
import com.btg.model.*;
import com.btg.repository.ClienteRepository;
import com.btg.service.IClienteService;
import com.btg.service.IInscripcionService;
import com.btg.service.INotificationService;
import com.btg.service.IProductoService;
import com.btg.util.EstadoInscripcion;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class InscripcionServiceImpl implements IInscripcionService {


    private final IClienteService clienteService;
    private final INotificationService notificacionService;
    private final IProductoService productoService;
 
    public InscripcionServiceImpl(IClienteService clienteService, 
	            INotificationService notificacionService, 
	            IProductoService productoService) {
	this.clienteService = clienteService;
	this.notificacionService = notificacionService;
	this.productoService = productoService;
	}

    @Override
    public void suscribir(Integer idCliente, Inscripcion inscripcion, String preferenciaNotificacion ) {
        // 1. Validar y obtener el cliente
        Cliente cliente = clienteService.findById(idCliente) ;

        if (cliente == null) {
            throw new ProductoNoEncontradoException("El cliente con ID " + idCliente + " no existe.");
        }

        Producto producto = productoService.findById(inscripcion.getIdProducto());
        if (producto == null) {
            throw new ProductoNoEncontradoException("El producto con ID " + inscripcion.getIdProducto() + " no existe.");
        }
        if (cliente.getSaldo() < inscripcion.getMonto()) {
            throw new SaldoInsuficienteException("No tiene saldo suficiente para vincularse al fondo " + producto.getNombre());
        }
		
		Inscripcion newInscripcion = new Inscripcion(inscripcion.getIdProducto(), inscripcion.getMonto(), 
				EstadoInscripcion.ACTIVA.getValor(), inscripcion.getFechaApertura(), null, Instant.now(), Instant.now());

        // 4. Actualizar cliente (Débito de saldo y agregar a la lista)
        cliente.setSaldo(cliente.getSaldo() - inscripcion.getMonto());
         
        if (cliente.getInscripciones() == null) {
            cliente.setInscripciones(new ArrayList<>());
        }
        cliente.getInscripciones().add(newInscripcion);

        // 5. Persistir el documento completo
        clienteService.save(cliente);
         
		 
		String mensaje = "Hola " + cliente.getNombre() + ", tu suscripción al fondo " 
                + producto.getNombre() + " - " +  newInscripcion.getIdProducto() + " ha sido exitosa.";

            // El 'tipo' viene de la preferencia del usuario (EMAIL o SMS)
        notificacionService.sendNotification(
               mensaje, 
               cliente.getCorreo(), 
               preferenciaNotificacion,
               cliente.getTelefono()
            );
			 
	} 

    @Override
    public Inscripcion cancelar(Integer idCliente, String idInscripcion, String preferenciaNotificacion) {
        Cliente cliente = clienteService.findById(idCliente) ;

        // Buscar la inscripción específica dentro del array por su ID único
        Inscripcion inscripcion = cliente.getInscripciones().stream()
                .filter(i -> i.getId().equals(idInscripcion))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        // Lógica de cancelación
        inscripcion.setEstado(EstadoInscripcion.INACTIVA.getValor());
        inscripcion.setFechaCancelacion(Instant.now());
        inscripcion.setUpdatedAt(Instant.now());

        inscripcion.setIdCancelacion( UUID.randomUUID().toString());
        
        // Devolver saldo (opcional, según tu regla de negocio)
        cliente.setSaldo(cliente.getSaldo() + inscripcion.getMonto());

        clienteService.save(cliente);
        Producto producto = productoService.findById(inscripcion.getIdProducto());
		String mensaje = "Hola " + cliente.getNombre() + ", tu suscripción al fondo " 
                + producto.getNombre() + " - " +  inscripcion.getIdProducto() + " ha sido cancelada.";

            // El 'tipo' viene de la preferencia del usuario (EMAIL o SMS)
        notificacionService.sendNotification(
               mensaje, 
               cliente.getCorreo(), 
               preferenciaNotificacion,
               cliente.getTelefono()
            );
        return inscripcion;
        
    }

    @Override
    public List<Inscripcion> obtenerHistorico(Integer idCliente) {
    	List<Inscripcion> lista = clienteService.findById(idCliente).getInscripciones();
        return lista != null ? lista : new ArrayList<>();
    }
}