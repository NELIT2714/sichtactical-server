package dev.nelit.server.repositories.event;

import dev.nelit.server.entity.event.EventLocation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EventLocationRepository extends ReactiveCrudRepository<EventLocation, Integer> {
    Mono<EventLocation> findByNameAndAddress(String name, String address);
}

