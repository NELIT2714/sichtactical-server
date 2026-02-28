package dev.nelit.server.repositories.notification;

import dev.nelit.server.entity.notification.Notification;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NotificationRepository extends ReactiveCrudRepository<Notification, Integer> {

    @Query("SELECT * FROM tbl_notifications LIMIT :limit OFFSET :offset")
    Flux<Notification> findAllPaged(int limit, int offset);

}
