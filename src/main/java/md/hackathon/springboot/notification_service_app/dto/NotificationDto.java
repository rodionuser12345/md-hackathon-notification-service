package md.hackathon.springboot.notification_service_app.dto;

import lombok.Data;
import md.hackathon.springboot.notification_service_app.enums.NotificationType;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private String status;
    private String message;
    private NotificationType type;
    private Long userId;
    private Long volunteerId;
    private LocalDateTime timestamp;
}
