package com.btg.service;

import com.btg.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse; // Importante
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse; // Importante
import software.amazon.awssdk.services.sns.model.SnsException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private SnsClient snsClient;

    @Mock
    private SesClient sesClient;

    @InjectMocks
    private NotificationServiceImpl notificationService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void debeEnviarSMSCuandoElTipoEsSMS() {
        
        
     // Arrange
        String mensaje = "Tu suscripción ha sido exitosa";
        String telefono = "+573157071649";
        ArgumentCaptor<PublishRequest> captor = ArgumentCaptor.forClass(PublishRequest.class);

        when(snsClient.publish(any(PublishRequest.class)))
                .thenReturn(PublishResponse.builder().messageId("sms-123").build());

        // Act
        notificationService.sendNotification(mensaje, "daniel.barrera.adame@gmail.com", "SMS", telefono);

        // Assert
        verify(snsClient).publish(captor.capture());
        assertEquals(mensaje, captor.getValue().message());
        assertEquals(telefono, captor.getValue().phoneNumber());
        verify(sesClient, never()).sendEmail(any(SendEmailRequest.class));
    }

    @Test
    void debeEnviarEmailCuandoElTipoEsEmail() {
        // 1. CONFIGURAR EL MOCK (Para evitar el null)
    	// Arrange
        String mensaje = "Bienvenido al fondo BTG";
        String correo = "daniel.barrera.adame@gmail.com";
        ArgumentCaptor<SendEmailRequest> captor = ArgumentCaptor.forClass(SendEmailRequest.class);

        when(sesClient.sendEmail(any(SendEmailRequest.class)))
                .thenReturn(SendEmailResponse.builder().messageId("email-123").build());

        // Act
        notificationService.sendNotification(mensaje, correo, "EMAIL", "+573157071649");

        // Assert
        
        verify(sesClient).sendEmail(captor.capture());
        SendEmailRequest request = captor.getValue();
        assertEquals(correo, request.destination().toAddresses().get(0));
        assertEquals(mensaje, request.message().body().text().data());
        verify(snsClient, never()).publish(any(PublishRequest.class));
    }
    
    @Test
    @DisplayName("Debe enviar por ambos canales si el tipo no es EMAIL ni SMS")
    void debeEnviarAmbosCuandoTipoEsDesconocido() {
        // Arrange
        when(snsClient.publish(any(PublishRequest.class)))
                .thenReturn(PublishResponse.builder().build());
        when(sesClient.sendEmail(any(SendEmailRequest.class)))
                .thenReturn(SendEmailResponse.builder().build());

        // Act
        notificationService.sendNotification("Mensaje", "daniel.barrera.adame@gmail.com", "OTRO", "123");

        // Assert
        verify(snsClient, times(1)).publish(any(PublishRequest.class));
        verify(sesClient, times(1)).sendEmail(any(SendEmailRequest.class));
    }

    @Test
    @DisplayName("No debe romper el flujo si AWS SES lanza una excepción")
    void debeManejarExcepcionDeSes() {
        // Arrange
        doThrow(SesException.builder().message("Error simulado de AWS").build())
                .when(sesClient).sendEmail(any(SendEmailRequest.class));

        // Act & Assert
        assertDoesNotThrow(() -> 
            notificationService.sendNotification("Msj", "daniel.barrera.adame@gmail.com", "EMAIL", "123")
        );
        verify(sesClient).sendEmail(any(SendEmailRequest.class));
    }

    @Test
    @DisplayName("No debe romper el flujo si AWS SNS lanza una excepción")
    void debeManejarExcepcionDeSns() {
        // Arrange
        doThrow(SnsException.builder().message("Error simulado de SNS").build())
                .when(snsClient).publish(any(PublishRequest.class));

        // Act & Assert
        assertDoesNotThrow(() -> 
            notificationService.sendNotification("Msj", "daniel.barrera.adame@gmail.com", "SMS", "123")
        );
        verify(snsClient).publish(any(PublishRequest.class));
    }
}