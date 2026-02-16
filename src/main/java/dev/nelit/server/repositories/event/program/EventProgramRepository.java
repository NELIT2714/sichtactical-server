package dev.nelit.server.repositories.event.program;

import dev.nelit.server.entity.event.program.EventProgram;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventProgramRepository extends ReactiveCrudRepository<EventProgram, Integer> {
    Flux<EventProgram> findByIdEvent(Integer idEvent);
}

