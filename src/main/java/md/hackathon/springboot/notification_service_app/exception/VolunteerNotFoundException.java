package md.hackathon.springboot.notification_service_app.exception;

public class VolunteerNotFoundException extends RuntimeException {

    public VolunteerNotFoundException(String message) {
        super(message);
    }
}
