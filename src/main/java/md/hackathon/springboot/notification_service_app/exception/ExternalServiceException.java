package md.hackathon.springboot.notification_service_app.exception;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }
}
