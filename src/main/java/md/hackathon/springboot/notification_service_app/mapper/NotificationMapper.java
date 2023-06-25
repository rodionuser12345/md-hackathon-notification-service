package md.hackathon.springboot.notification_service_app.mapper;

import md.hackathon.springboot.notification_service_app.dto.NotificationDto;
import md.hackathon.springboot.notification_service_app.enums.NotificationType;
import md.hackathon.springboot.notification_service_app.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDto toDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setStatus(notification.getStatus());
        dto.setMessage(notification.getMessage());
        dto.setType(NotificationType.valueOf(notification.getType()));
        dto.setUserId(notification.getUserId());
        dto.setVolunteerId(notification.getVolunteerId());
        dto.setTimestamp(notification.getTimestamp());
        return dto;
    }

    public Notification toEntity(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setStatus(dto.getStatus());
        notification.setMessage(dto.getMessage());
        notification.setType(String.valueOf(dto.getType()));
        // User and Volunteer will be set in the service
        notification.setTimestamp(dto.getTimestamp());
        return notification;
    }
}

