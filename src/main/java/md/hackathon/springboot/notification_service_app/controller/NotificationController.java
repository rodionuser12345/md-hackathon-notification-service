package md.hackathon.springboot.notification_service_app.controller;

import md.hackathon.springboot.notification_service_app.model.ChatMessage;
import md.hackathon.springboot.notification_service_app.model.Notification;
import md.hackathon.springboot.notification_service_app.model.SystemNotification;
import md.hackathon.springboot.notification_service_app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/send")
    @SendTo("/topic/notifications")
    public Notification send(Notification notification) {
        return notification;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage handleChatMessage(ChatMessage message) {
        // log message
        System.out.println("Received chat message: " + message.getContent());

        // return message to be broadcast to subscribers
        return message;
    }

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public SystemNotification handleSystemNotification(SystemNotification notification) {
        // log notification
        System.out.println("Received system notification: " + notification.getContent());

        // return notification to be broadcast to subscribers
        return notification;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        return new ResponseEntity<>(notificationService.createNotification(notification), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return new ResponseEntity<>(notificationService.getNotificationById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(notificationService.getNotificationsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<Notification>> getNotificationsByVolunteerId(@PathVariable Long volunteerId) {
        return new ResponseEntity<>(notificationService.getNotificationsByVolunteerId(volunteerId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification) {
        return new ResponseEntity<>(notificationService.updateNotification(notification), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @PathVariable String status) {
        notificationService.updateStatus(id, status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}