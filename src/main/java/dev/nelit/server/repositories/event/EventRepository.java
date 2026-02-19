package dev.nelit.server.repositories.event;

import dev.nelit.server.entity.event.Event;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventRepository extends ReactiveCrudRepository<Event, Integer> {
    @Query("""
        SELECT * FROM tbl_events
        ORDER BY event_date ASC, start_time ASC
        LIMIT :limit OFFSET :offset
    """)
    Flux<Event> findAllPaged(int limit, int offset);

    @Query("SELECT COUNT(*) FROM tbl_events")
    Mono<Long> countAll();
}

