package dev.nelit.server.repositories.event;

import dev.nelit.server.entity.event.EventLocation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLocationRepository extends ReactiveCrudRepository<EventLocation, Integer> {
}

