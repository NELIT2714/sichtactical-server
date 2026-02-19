package dev.nelit.server.repositories.event;

import dev.nelit.server.entity.event.EventMember;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventMemberRepository extends ReactiveCrudRepository<EventMember, Integer> {
    Mono<Boolean> existsEventMemberByIdEventAndIdUser(Integer idEvent, Integer idUser);

    Flux<EventMember> getEventMembersByIdEvent(Integer idEvent);

    Mono<Integer> countEventMemberByIdEventAndIdUser(Integer idEvent, Integer idUser);

    Mono<Void> deleteByIdEventAndIdUser(Integer idEvent, Integer idUser);
}
