package dev.nelit.server.repositories.event.program;

import dev.nelit.server.entity.event.program.EventProgramI18n;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface EventProgramI18nRepository extends ReactiveCrudRepository<EventProgramI18n, Integer> {
    Mono<Void> deleteByIdEventProgram(Integer idEventProgram);
    Flux<EventProgramI18n> findByIdEventProgramIn(Collection<Integer> idEventPrograms);
    Mono<EventProgramI18n> findByIdEventProgramAndLang(Integer idEventProgram, String lang);
}
