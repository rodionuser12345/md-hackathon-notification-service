package md.hackathon.springboot.notification_service_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemNotification {

    private String type;
    private String content;
    private Instant timestamp;

}