package dev.nelit.server.services.event.impl;

import dev.nelit.server.components.MessageProvider;
import dev.nelit.server.dto.event.EventMemberDataDTO;
import dev.nelit.server.dto.event.EventSignUpDTO;
import dev.nelit.server.dto.notification.NotificationDataDTO;
import dev.nelit.server.dto.notification.NotificationUpsertDTO;
import dev.nelit.server.dto.user.UserTelegramIdAndLanguageCodeDTO;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.event.EventData;
import dev.nelit.server.entity.event.EventMember;
import dev.nelit.server.entity.user.User;
import dev.nelit.server.enums.NotificationCategories;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.mappers.event.EventMemberDataMapper;
import dev.nelit.server.mappers.NotificationMapper;
import dev.nelit.server.repositories.event.EventDataRepository;
import dev.nelit.server.repositories.event.EventMemberRepository;
import dev.nelit.server.repositories.user.UserTelegramDataRepository;
import dev.nelit.server.services.event.api.EventMemberService;
import dev.nelit.server.services.notification.api.NotificationPublisherService;
import dev.nelit.server.services.notification.api.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class EventMemberServiceImpl implements EventMemberService {

    @Value("${app.timezone}")
    private String timezone;

    private final EventMemberRepository eventMemberRepository;
    private final EventDataRepository eventDataRepository;
    private final UserTelegramDataRepository userTelegramDataRepository;
    private final NotificationService notificationService;
    private final NotificationPublisherService publisher;
    private final MessageProvider messageProvider;
    private final TransactionalOperator tx;

    public EventMemberServiceImpl(EventMemberRepository eventMemberRepository,
                                  EventDataRepository eventDataRepository,
                                  UserTelegramDataRepository userTelegramDataRepository,
                                  NotificationService notificationService,
                                  NotificationPublisherService publisher,
                                  MessageProvider messageProvider,
                                  TransactionalOperator tx) {
        this.eventMemberRepository = eventMemberRepository;
        this.eventDataRepository = eventDataRepository;
        this.userTelegramDataRepository = userTelegramDataRepository;
        this.notificationService = notificationService;
        this.publisher = publisher;
        this.messageProvider = messageProvider;
        this.tx = tx;
    }

    @Override
    public Mono<EventMemberDataDTO> getLastMemberData(int userID) {
        return eventMemberRepository.findFirstByIdUserOrderByIdEventMember(userID)
            .map(EventMemberDataMapper::toResponse);
    }

    @Override
    public Mono<List<EventMemberDataDTO>> getMembers(Event event) {
        return eventMemberRepository.findEventMembersByIdEventAndRegisteredIsTrue(event.getIdEvent())
            .map(EventMemberDataMapper::toResponse)
            .collectList();
    }

    @Override
    public Mono<Void> signUp(Event event, User user, EventSignUpDTO dto) {
        return tx.transactional(
            eventMemberRepository.getEventMemberByIdEventAndIdUser(event.getIdEvent(), user.getIdUser())
                .flatMap(member -> {
                    if (Boolean.TRUE.equals(member.getRegistered())) {
                        return Mono.error(new HTTPException(HttpStatus.CONFLICT, "User is already signed up for this event"));
                    }
                    member.setRegistered(true);
                    member.setFullName(dto.getFullName());
                    member.setCallSign(dto.getCallSign() != null && !dto.getCallSign().isBlank() ? dto.getCallSign() : null);
                    member.setPhoneNumber(dto.getPhoneNumber());
                    member.setEquipment(dto.getEquipmentType());
                    member.setUpdateTimestamp(LocalDateTime.now(ZoneId.of(timezone)));
                    return eventMemberRepository.save(member);
                })
                .switchIfEmpty(Mono.defer(() -> eventMemberRepository.save(new EventMember(
                    event.getIdEvent(),
                    user.getIdUser(),
                    dto.getFullName(),
                    dto.getCallSign() != null && !dto.getCallSign().isBlank() ? dto.getCallSign() : null,
                    dto.getPhoneNumber(),
                    dto.getEquipmentType()
                ))))
                .onErrorMap(DuplicateKeyException.class,
                    ex -> new HTTPException(HttpStatus.CONFLICT, "User is already signed up for this event")
                )
                .then(sendNotification(event, user))
        );
    }

    @Override
    public Mono<Void> signOut(int eventID, int userID) {
        return eventMemberRepository.getEventMemberByIdEventAndIdUser(eventID, userID)
            .flatMap(member -> {
                member.setRegistered(false);
                member.setUpdateTimestamp(LocalDateTime.now(ZoneId.of(timezone)));
                return eventMemberRepository.save(member);
            })
            .then();
    }

    private Mono<Void> sendNotification(Event event, User user) {
        Mono<UserTelegramIdAndLanguageCodeDTO> userTelegramMono = userTelegramDataRepository
            .findById(user.getIdUserTelegramData())
            .map(td -> new UserTelegramIdAndLanguageCodeDTO(td.getTelegramId(), td.getLanguageCode()));

        Mono<Map<String, String>> eventNameByLangMono = eventDataRepository
            .findByIdEvent(event.getIdEvent())
            .collectMap(EventData::getLang, EventData::getName);

        return Mono.zip(eventNameByLangMono, userTelegramMono)
            .flatMap(tuple -> {
                Map<String, String> eventNameByLang = tuple.getT1();
                UserTelegramIdAndLanguageCodeDTO userTelegram = tuple.getT2();

                Mono<String> fallbackNameMono = eventNameByLang.isEmpty()
                    ? Mono.just("")
                    : Mono.just(eventNameByLang.values().iterator().next());

                return fallbackNameMono.flatMap(defaultName ->
                    Flux.fromIterable(messageProvider.getAvailableLanguages())
                        .flatMap(lang -> {
                            String eventName = eventNameByLang.getOrDefault(lang, defaultName);
                            Map<String, String> params = Map.of("event_name", eventName);

                            return Mono.zip(
                                messageProvider.get(lang, "notifications.signup.title"),
                                messageProvider.get(lang, "notifications.signup.description", params)
                            ).map(t -> Map.entry(lang, new NotificationDataDTO(t.getT1(), t.getT2(), null)));
                        })
                        .collectMap(Map.Entry::getKey, Map.Entry::getValue)
                        .flatMap(localized -> {
                            NotificationUpsertDTO notificationDTO = new NotificationUpsertDTO(NotificationCategories.SIGNUP, localized);

                            return notificationService.upsertNotification(notificationDTO)
                                .flatMap(notification ->
                                    notificationService.getNotificationI18n(notification.getIdNotification())
                                        .collectList()
                                        .flatMap(i18nList -> publisher.publish(
                                            "notifications.signup",
                                            Map.of(
                                                "notification", NotificationMapper.toResponseDTO(notification, i18nList),
                                                "users", List.of(userTelegram)
                                            )
                                        ))
                                );
                        })
                );
            });
    }
}
