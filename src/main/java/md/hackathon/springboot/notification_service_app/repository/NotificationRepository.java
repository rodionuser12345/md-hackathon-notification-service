package md.hackathon.springboot.notification_service_app.repository;

import jakarta.transaction.Transactional;
import md.hackathon.springboot.notification_service_app.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    List<Notification> findByVolunteerId(Long volunteerId);

    @Transactional
    @Modifying
    @Query("UPDATE Notification n SET n.status = :status WHERE n.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
}
