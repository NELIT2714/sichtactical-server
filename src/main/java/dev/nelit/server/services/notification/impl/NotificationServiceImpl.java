package dev.nelit.server.services.notification.impl;

import dev.nelit.server.dto.notification.NotificationDataDTO;
import dev.nelit.server.dto.notification.NotificationPageResponseDTO;
import dev.nelit.server.dto.notification.NotificationResponseDTO;
import dev.nelit.server.dto.notification.NotificationUpsertDTO;
import dev.nelit.server.entity.notification.Notification;
import dev.nelit.server.entity.notification.NotificationI18n;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.mappers.NotificationMapper;
import dev.nelit.server.repositories.notification.NotificationI18nRepository;
import dev.nelit.server.repositories.notification.NotificationRepository;
import dev.nelit.server.services.notification.api.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${app.timezone}")
    private String timezone;

    private final NotificationRepository notificationRepository;
    private final NotificationI18nRepository notificationI18nRepository;
    private final TransactionalOperator tx;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationI18nRepository notificationI18nRepository, TransactionalOperator tx) {
        this.notificationRepository = notificationRepository;
        this.notificationI18nRepository = notificationI18nRepository;
        this.tx = tx;
    }


    @Override
    public Mono<NotificationPageResponseDTO> getNotifications(int page, int size) {
        int offset = page * size;

        Mono<Long> totalMono = notificationRepository.count();
        Flux<Notification> notificationFlux = notificationRepository.findAllPaged(size, offset);

        Flux<NotificationResponseDTO> responseDtoFlux = notificationFlux.concatMap(notification ->
            notificationI18nRepository.findByIdNotification(notification.getIdNotification())
                .collectList()
                .map(i18nList -> NotificationMapper.toResponseDTO(notification, i18nList))
        );

        return Mono.zip(totalMono, responseDtoFlux.collectList())
            .map(tuple -> new NotificationPageResponseDTO(
                tuple.getT2(),
                tuple.getT1(),
                (int) Math.ceil((double) tuple.getT1() / size),
                page + 1,
                size
            ));
    }

    @Override
    public Mono<NotificationResponseDTO> getNotificationResponse(int notificationID) {
        return notificationRepository.findById(notificationID)
            .switchIfEmpty(Mono.error(new HTTPException(HttpStatus.NOT_FOUND, "Notification not found: " + notificationID)))
            .flatMap(notification ->
                notificationI18nRepository.findByIdNotification(notification.getIdNotification())
                    .collectList()
                    .map(i18nList -> NotificationMapper.toResponseDTO(notification, i18nList))
            );
    }

    @Override
    public Mono<Notification> upsertNotification(NotificationUpsertDTO dto) {
        return tx.transactional(
            Mono.defer(() -> {
                Map<String, NotificationDataDTO> notificationDataDTO = dto.getNotificationData();

                if (dto.getIdNotification() == null) {
                    return notificationRepository.save(new Notification(dto.getCategory(), LocalDateTime.now(ZoneId.of(timezone))))
                        .flatMap(notification ->
                            Flux.fromIterable(notificationDataDTO == null ? Map.<String, NotificationDataDTO>of().entrySet() : notificationDataDTO.entrySet())
                                .flatMap(entry -> notificationI18nRepository.save(new NotificationI18n(
                                    notification.getIdNotification(),
                                    entry.getKey(),
                                    entry.getValue().title(),
                                    entry.getValue().description(),
                                    entry.getValue().content()
                                )))
                                .then().thenReturn(notification)
                        );
                } else {
                    return notificationRepository.findById(dto.getIdNotification())
                        .switchIfEmpty(Mono.error(new HTTPException(HttpStatus.NOT_FOUND, "Notification not found: " + dto.getIdNotification())))
                        .flatMap(notification ->
                            notificationI18nRepository.deleteByIdNotification(notification.getIdNotification())
                                .then(
                                    Flux.fromIterable(notificationDataDTO == null ? Map.<String, NotificationDataDTO>of().entrySet() : notificationDataDTO.entrySet())
                                        .flatMap(entry -> notificationI18nRepository.save(new NotificationI18n(
                                            notification.getIdNotification(),
                                            entry.getKey(),
                                            entry.getValue().title(),
                                            entry.getValue().description(),
                                            entry.getValue().content()
                                        )))
                                        .then().thenReturn(notification)
                                )
                        );
                }
            })
        );
    }

    @Override
    public Flux<NotificationI18n> getNotificationI18n(int notificationID) {
        return notificationI18nRepository.findAllByIdNotification(notificationID);
    }

    @Override
    public Mono<Void> deleteNotification(int notificationID) {
        return tx.transactional(notificationRepository.deleteById(notificationID));
    }
}
