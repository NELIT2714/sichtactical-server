package dev.nelit.server.repositories.notification;

import dev.nelit.server.entity.notification.NotificationI18n;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface NotificationI18nRepository extends ReactiveCrudRepository<NotificationI18n, Integer> {
    Mono<Void> deleteByIdNotification(Integer idNotification);

    Flux<NotificationI18n> findAllByIdNotification(Integer idNotification);
}
