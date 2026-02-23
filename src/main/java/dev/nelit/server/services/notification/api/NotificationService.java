package dev.nelit.server.services.notification.api;

import dev.nelit.server.dto.notification.NotificationUpsertDTO;
import dev.nelit.server.entity.notification.Notification;
import dev.nelit.server.entity.notification.NotificationI18n;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<Notification> upsertNotification(NotificationUpsertDTO dto);

    Flux<NotificationI18n> getNotificationI18n(int notificationID);

    Mono<Void> deleteNotification(int notificationID);
}
