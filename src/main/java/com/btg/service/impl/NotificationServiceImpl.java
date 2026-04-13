package com.btg.service.impl;

import com.btg.service.INotificationService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest; 
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message; 
import software.amazon.awssdk.services.ses.model.SesException; 

@Service
@Slf4j 
@RequiredArgsConstructor // Esta anotación crea el constructor para los campos 'final'
public class NotificationServiceImpl implements INotificationService {
	

	 
    private final SnsClient snsClient;
    private final SesClient sesClient;

    @Override
    public void sendNotification(String mensaje, String destino, String tipo, String telefono) {
        if ("EMAIL".equalsIgnoreCase(tipo)) {
            sendEmail(mensaje, destino);
        } else if ("SMS".equalsIgnoreCase(tipo)) {
            sendSMS(mensaje, telefono);
        } else {
            sendSMS(mensaje, telefono);
            sendEmail(mensaje, destino);
            log.warn("Tipo de notificación no soportado: {}", tipo);
        }
    }

 

    private void sendSMS(String mensaje, String telefono) {
    	try {

            PublishRequest request = PublishRequest.builder()
                    .message(mensaje)
                    .phoneNumber(telefono)
                    .build();
            Object o = snsClient.publish(request);
            System.out.println(o);
            System.out.println(o);
    	}catch (Exception e) {
			// TODO: handle exception
    	  	e.printStackTrace();
            log.error("Error al enviar SMS mediante AWS SNS: {}", e.getMessage());

            // Aquí podrías lanzar una excepción personalizada de negocio si es crítico
       }
    }

    private void sendEmail(String mensaje, String emailDestino) {
        // IMPORTANTE: Este correo debe estar verificado en tu consola de AWS SES
        String remitente = "daniel.barrera.adame@gmail.com"; 
        String asunto = "Notificación de Suscripción - BTG Pactual";

        try {
            // 1. Configurar el destino (To)
            Destination destination = Destination.builder()
                    .toAddresses(emailDestino)
                    .build();

            // 2. Configurar el contenido del mensaje
            Content subject = Content.builder().data(asunto).build();
            Content bodyContent = Content.builder().data(mensaje).build();
            
            Body body = Body.builder()
                    .text(bodyContent) // Puedes usar .html() si envías una plantilla HTML
                    .build();

            Message message = Message.builder()
                    .subject(subject)
                    .body(body)
                    .build();

            // 3. Construir la petición completa
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(destination)
                    .message(message)
                    .source(remitente)
                    .build();

            // 4. Enviar mediante el cliente inyectado
            log.info("Intentando enviar correo SES a: {}", emailDestino);
            Object o = sesClient.sendEmail(emailRequest);
            log.info("¡Correo enviado exitosamente!");

            System.out.println(o);
            System.out.println(o);
        } catch (SesException e) {
        	e.printStackTrace(); 
        	 String errorMsg = (e.awsErrorDetails() != null)
        	            ? e.awsErrorDetails().errorMessage()
        	            : e.getMessage();
 
        	    log.error("Error al enviar SMS mediante AWS SNS: {}", e.getMessage());

        	}
    }
}
