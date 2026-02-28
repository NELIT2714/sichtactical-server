package dev.nelit.server.services.notification.api;

import reactor.core.publisher.Mono;

public interface NotificationPublisherService {
    Mono<Void> publish(String routingKey, Object payload);
}
