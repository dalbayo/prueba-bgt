package com.btg.service;

public interface INotificationService {
    void sendNotification(String mensaje, String destino, String tipo, String telefono);
}
