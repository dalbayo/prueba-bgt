package com.btg.controller;

import com.btg.model.Inscripcion; 
import com.btg.service.IClienteService;
import com.btg.service.IInscripcionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/v1/suscripciones") 
public class InscripcionController {
 
    private final IInscripcionService inscripcionService;
 

	public InscripcionController(IInscripcionService inscripcionService) {
		 
		this.inscripcionService = inscripcionService;
	}

    // 1. Suscribirse a un nuevo fondo (Apertura)
    @PostMapping("/apertura")
    public ResponseEntity<?> suscribir(@RequestBody Inscripcion inscripcion,  @RequestParam Integer idCliente, @RequestParam String preferenciaNotificacion ) {
        // Lógica: Validar saldo del cliente, crear inscripción y enviar notificación
    	
    	inscripcionService.suscribir(idCliente, inscripcion, preferenciaNotificacion); 
         
        
        return ResponseEntity.ok("Suscripción realizada con éxito. Notificación enviada "  );
    }

    // 2. Cancelar la suscripción a un fondo actual
    @PostMapping("/cancelar")
    public ResponseEntity<?> cancelar(@RequestParam Integer idCliente, @RequestParam String idInscripcion, @RequestParam String preferenciaNotificacion ) {
        // En DynamoDB usamos PK (Cliente) y SK (Inscripcion#Producto)
    	Inscripcion inscripcion = inscripcionService.cancelar(idCliente, idInscripcion, preferenciaNotificacion);
 
        if (inscripcion != null) { 
            return ResponseEntity.ok("Suscripción cancelada correctamente.");
        }
        return ResponseEntity.badRequest().body("No se encontró la suscripción.");
    }

    // 3. Ver historial de transacciones (Por Cliente)
    @GetMapping("/historial/{idCliente}")
    public ResponseEntity<List<Inscripcion>> obtenerHistorial(@PathVariable Integer idCliente) {
        String pk = "CLIENTE#" + idCliente;
        List<Inscripcion> historial = inscripcionService.obtenerHistorico(idCliente);
        return ResponseEntity.ok(historial);
    }

}
