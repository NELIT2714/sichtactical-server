package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventSignUpDTO;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.event.EventMember;
import dev.nelit.server.entity.user.User;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.repositories.event.EventMemberRepository;
import dev.nelit.server.services.event.api.EventMemberService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventMemberServiceImpl implements EventMemberService {

    private final EventMemberRepository eventMemberRepository;

    private final TransactionalOperator tx;

    public EventMemberServiceImpl(EventMemberRepository eventMemberRepository, TransactionalOperator tx) {
        this.eventMemberRepository = eventMemberRepository;
        this.tx = tx;
    }

    @Override
    public Mono<Void> signUpForEvent(Event event, User user, EventSignUpDTO dto) {
        return tx.transactional(eventMemberRepository.save(new EventMember(
                event.getIdEvent(),
                user.getIdUser(),
                dto.getFullName(),
                dto.getCallSign(),
                dto.getPhoneNumber(),
                dto.getEquipmentType()
            )))
            .onErrorMap(DuplicateKeyException.class,
                ex -> new HTTPException(HttpStatus.CONFLICT, "User is already signed up for this event")
            )
            .then();
    }
}
