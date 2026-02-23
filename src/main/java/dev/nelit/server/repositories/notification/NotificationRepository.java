package dev.nelit.server.repositories.notification;

import dev.nelit.server.entity.notification.Notification;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends ReactiveCrudRepository<Notification, Integer> {
}
