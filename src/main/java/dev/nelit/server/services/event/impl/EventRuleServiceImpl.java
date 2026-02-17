package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventRuleDTO;
import dev.nelit.server.entity.event.rule.EventRule;
import dev.nelit.server.entity.event.rule.EventRuleI18n;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.repositories.event.rule.EventRuleI18nRepository;
import dev.nelit.server.repositories.event.rule.EventRuleRepository;
import dev.nelit.server.services.event.api.EventRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EventRuleServiceImpl implements EventRuleService {

    private final EventRuleRepository eventRuleRepository;
    private final EventRuleI18nRepository eventRuleI18nRepository;

    private final TransactionalOperator tx;

    public EventRuleServiceImpl(EventRuleRepository eventRuleRepository, EventRuleI18nRepository eventRuleI18nRepository, TransactionalOperator tx) {
        this.eventRuleRepository = eventRuleRepository;
        this.eventRuleI18nRepository = eventRuleI18nRepository;
        this.tx = tx;
    }

    @Override
    public Mono<Void> upsertEventRules(int eventID, Map<String, List<EventRuleDTO>> rules) {
        if (rules == null || rules.isEmpty()) return Mono.empty();

        String firstLang = rules.keySet().iterator().next();
        List<EventRuleDTO> structure = rules.get(firstLang);

        return tx.transactional(Flux.fromIterable(structure)
            .flatMap(dto -> {
                Mono<EventRule> ruleMono;

                if (dto.getIdEventRule() != null) {
                    ruleMono = eventRuleRepository.findById(dto.getIdEventRule())
                        .flatMap(existing -> {
                            if (!existing.getIdEvent().equals(eventID)) return Mono.error(new HTTPException(HttpStatus.CONFLICT, "Rule belongs to different event"));
                            existing.setPosition(dto.getPosition());
                            return eventRuleRepository.save(existing);
                        })
                        .switchIfEmpty(Mono.error(new HTTPException(HttpStatus.NOT_FOUND, "Rule with id " + dto.getIdEventRule() + " not found")));
                } else {
                    ruleMono = eventRuleRepository.save(new EventRule(eventID, dto.getPosition()));
                }

                return ruleMono.flatMap(savedRule -> eventRuleI18nRepository.deleteByIdEventRule(savedRule.getIdEventRule())
                    .thenMany(
                        Flux.fromIterable(rules.entrySet())
                            .flatMap(entry -> {
                                String lang = entry.getKey();
                                EventRuleDTO langDto = entry.getValue().stream()
                                    .filter(d -> Objects.equals(d.getPosition(), dto.getPosition()))
                                    .findFirst()
                                    .orElse(null);

                                if (langDto != null && langDto.getText() != null && !langDto.getText().isBlank()) {
                                    return eventRuleI18nRepository.save(
                                        new EventRuleI18n(null, savedRule.getIdEventRule(), lang, langDto.getText())
                                    );
                                }
                                return Mono.empty();
                            })
                    )
                    .then());
            })
            .then());
    }
}