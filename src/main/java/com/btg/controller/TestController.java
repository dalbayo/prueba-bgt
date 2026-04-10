package com.btg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btg.service.INotificationService;

import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/test")
// @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TestController {



    private final INotificationService notificacionService;
    
 // EL CONSTRUCTOR ES LA CLAVE: Spring busca una implementación de INotificationService y la inyecta aquí
    public TestController(INotificationService notificacionService) {
        this.notificacionService = notificacionService;
    }
    
    @GetMapping("/status")
    public Map<String, String> getStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("estado", "OK");
        response.put("mensaje", "Backend de BTG conectado exitosamente");
        try {
        	String mensaje = "Hola Daniel , tu suscripción al fondo  prueba producto  ha sido exitosa.";

    			// El 'tipo' viene de la preferencia del usuario (EMAIL o SMS)
            notificacionService.sendNotification(
    			   mensaje, 
    			   "daniel.barrera.adame@gmail.com", 
    			   "SMS",// "CORREO-SMS",
    			   "+573157071649"
    			);
        }catch (Exception e) {
			// TODO: handle exception
		}
        return response;
    }
}
