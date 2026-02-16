package dev.nelit.server.repositories.event;

import dev.nelit.server.entity.event.EventData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventDataRepository extends ReactiveCrudRepository<EventData, Integer> {
    Mono<Void> deleteByIdEvent(Integer idEvent);
    Flux<EventData> findByIdEvent(Integer idEvent);
}
