package org.example.clients.notification;

public record NotificationCreateDto(
        Integer customerId,
        String message
) {
}
