package co.tz.qroo.spa.notifications;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationsRepository extends JpaRepository<Notifications, UUID> {
}
