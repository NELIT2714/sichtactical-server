package dev.nelit.server.repositories.event.rule;

import dev.nelit.server.entity.event.rule.EventRule;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventRuleRepository extends ReactiveCrudRepository<EventRule, Integer> {
    Flux<EventRule> findByIdEvent(Integer idEvent);

    Flux<EventRule> getAllByIdEvent(Integer idEvent);
}

