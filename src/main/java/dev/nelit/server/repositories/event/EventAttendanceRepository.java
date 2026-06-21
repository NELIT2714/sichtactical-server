package dev.nelit.server.repositories.event;

import dev.nelit.server.entity.event.EventAttendance;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface EventAttendanceRepository extends ReactiveCrudRepository<EventAttendance, Integer> {
    Mono<EventAttendance> findByIdEventAndIdUser(Integer idEvent, Integer idUser);
    Flux<EventAttendance> findByIdEventIn(Collection<Integer> idEvents);
    Flux<EventAttendance> findByIdEventAndAttendedIsTrue(Integer idEvent);
    Mono<Integer> countByIdUserAndAttendedIsTrue(Integer idUser);
}
