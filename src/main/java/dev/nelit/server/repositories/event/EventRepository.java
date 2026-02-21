package dev.nelit.server.repositories.event;

import dev.nelit.server.dto.event.EventResponseDTO;
import dev.nelit.server.entity.event.Event;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
public interface EventRepository extends ReactiveCrudRepository<Event, Integer> {
    @Query("""
        SELECT * FROM tbl_events
        WHERE event_date >= :today
        ORDER BY event_date ASC, start_time ASC
        LIMIT :limit OFFSET :offset
    """)
    Flux<Event> findAllPaged(@Param("today") LocalDate today, int limit, int offset);

    @Query("SELECT COUNT(*) FROM tbl_events")
    Mono<Long> countAll();

    @Query("""
        SELECT * FROM tbl_events
        WHERE
            event_date > :today
            OR (event_date = :today AND start_time > :nowTime)
        ORDER BY event_date ASC, start_time ASC
        LIMIT 1
    """)
    Mono<Event> findNearestUpcoming(
        @Param("today") LocalDate today,
        @Param("nowTime") LocalTime nowTime
    );
}

