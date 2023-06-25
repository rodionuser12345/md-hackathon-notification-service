package md.hackathon.springboot.notification_service_app.service;

import md.hackathon.springboot.notification_service_app.dto.UserDto;
import md.hackathon.springboot.notification_service_app.dto.VolunteerDto;
import md.hackathon.springboot.notification_service_app.exception.ExternalServiceException;
import md.hackathon.springboot.notification_service_app.exception.NotificationNotFoundException;
import md.hackathon.springboot.notification_service_app.exception.UserNotFoundException;
import md.hackathon.springboot.notification_service_app.exception.VolunteerNotFoundException;
import md.hackathon.springboot.notification_service_app.model.Notification;
import md.hackathon.springboot.notification_service_app.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, RestTemplate restTemplate,
                                   WebClient webClient) {
        this.notificationRepository = notificationRepository;
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getNotificationsByVolunteerId(Long volunteerId) {
        return notificationRepository.findByVolunteerId(volunteerId);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, String status) {
        notificationRepository.updateStatus(id, status);
    }

    private UserDto getUserByIdRestTemplate(Long userId) {
        String userServiceUrl = "http://user-service/api/users/" + userId;
        return restTemplate.getForObject(userServiceUrl, UserDto.class);
    }

    private VolunteerDto getVolunteerByIdRestTemplate(Long volunteerId) {
        String volunteerServiceUrl = "http://volunteer-service/api/volunteers/" + volunteerId;
        return restTemplate.getForObject(volunteerServiceUrl, VolunteerDto.class);
    }

    private Mono<UserDto> getUserByIdWebClient(Long userId) {
        return webClient.get()
                .uri("/api/users/" + userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new UserNotFoundException("User with id " + userId + " not found")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ExternalServiceException("Error occurred while calling User Service")))
                .bodyToMono(UserDto.class);
    }

    private Mono<VolunteerDto> getVolunteerByIdWebClient(Long volunteerId) {
        return webClient.get()
                .uri("/api/volunteers/" + volunteerId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new VolunteerNotFoundException("Volunteer with id " + volunteerId + " not found")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ExternalServiceException("Error occurred while calling Volunteer Service")))
                .bodyToMono(VolunteerDto.class);
    }
}