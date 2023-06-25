package md.hackathon.springboot.notification_service_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String sender;
    private String content;
    private Instant timestamp;

}
