package md.hackathon.springboot.notification_service_app.service;

import md.hackathon.springboot.notification_service_app.model.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);

    Notification getNotificationById(Long id);

    List<Notification> getNotificationsByUserId(Long userId);

    List<Notification> getNotificationsByVolunteerId(Long volunteerId);

    Notification updateNotification(Notification notification);

    void deleteNotification(Long id);

    void updateStatus(Long id, String status);
}
