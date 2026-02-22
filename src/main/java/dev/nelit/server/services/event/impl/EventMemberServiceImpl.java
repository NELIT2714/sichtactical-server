package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventMemberDataDTO;
import dev.nelit.server.dto.event.EventSignUpDTO;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.event.EventMember;
import dev.nelit.server.entity.user.User;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.mappers.EventMemberDataMapper;
import dev.nelit.server.repositories.event.EventMemberRepository;
import dev.nelit.server.services.event.api.EventMemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class EventMemberServiceImpl implements EventMemberService {

    @Value("${app.timezone}")
    private String timezone;

    private final EventMemberRepository eventMemberRepository;

    private final TransactionalOperator tx;

    public EventMemberServiceImpl(EventMemberRepository eventMemberRepository, TransactionalOperator tx) {
        this.eventMemberRepository = eventMemberRepository;
        this.tx = tx;
    }

    @Override
    public Mono<EventMemberDataDTO> getLastMemberData(int userID) {
        return eventMemberRepository.findFirstByIdUserOrderByIdEventMember(userID)
            .map(EventMemberDataMapper::toResponse);
    }

    @Override
    public Mono<Void> signUp(Event event, User user, EventSignUpDTO dto) {
        return tx.transactional(
            eventMemberRepository.getEventMemberByIdEventAndIdUser(event.getIdEvent(), user.getIdUser())
                .flatMap(member -> {
                    // Обновить полностью юзера
                    member.setRegistered(true);
                    member.setEquipment(dto.getEquipmentType());
                    member.setUpdateTimestamp(LocalDateTime.now(ZoneId.of(timezone)));
                    return eventMemberRepository.save(member);
                })
                .switchIfEmpty(eventMemberRepository.save(new EventMember(
                    event.getIdEvent(),
                    user.getIdUser(),
                    dto.getFullName(),
                    dto.getCallSign().isBlank() ? null : dto.getCallSign(),
                    dto.getPhoneNumber(),
                    dto.getEquipmentType()
                )))
                .onErrorMap(DuplicateKeyException.class,
                ex -> new HTTPException(HttpStatus.CONFLICT, "User is already signed up for this event")
                )
                .then()
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
}
