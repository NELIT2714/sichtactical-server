package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.EventRuleDTO;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface EventRuleService {
    Mono<Void> upsertEventRules(int eventID, Map<String, List<EventRuleDTO>> rules);
}
