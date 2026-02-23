package dev.nelit.server.services.notification;

import dev.nelit.server.dto.notification.NotificationUpsertDTO;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<Void> upsertNotification(NotificationUpsertDTO dto);

    Mono<Void> deleteNotification(int notificationID);
}
