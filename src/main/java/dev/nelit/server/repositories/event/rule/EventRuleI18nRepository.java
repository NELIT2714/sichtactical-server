package dev.nelit.server.repositories.event.rule;

import dev.nelit.server.entity.event.rule.EventRuleI18n;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface EventRuleI18nRepository extends ReactiveCrudRepository<EventRuleI18n, Integer> {
    Mono<Void> deleteByIdEventRule(Integer idEventRule);
    Flux<EventRuleI18n> findByIdEventRuleIn(Collection<Integer> idEventRules);
    Mono<EventRuleI18n> findByIdEventRuleAndLang(Integer idEventRule, String lang);
}
