package dev.nelit.server.services.notification.impl;

import dev.nelit.server.config.RabbitConfig;
import dev.nelit.server.services.notification.api.NotificationPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tools.jackson.databind.ObjectMapper;

@Service
public class NotificationPublisherServiceImpl implements NotificationPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationPublisherServiceImpl.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public NotificationPublisherServiceImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> publish(String routingKey, Object payload) {
        return Mono.fromCallable(() -> {
                byte[] bytes = objectMapper.writeValueAsBytes(payload);
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, routingKey, bytes);
                return bytes;
            })
            .subscribeOn(Schedulers.boundedElastic())
            .doOnSuccess(v -> logger.info("Message published to routingKey={}", routingKey))
            .doOnError(e -> logger.error("Failed to publish to routingKey={}", routingKey, e))
            .then();
    }
}
