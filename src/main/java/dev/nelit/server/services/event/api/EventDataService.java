package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.EventDataDTO;
import dev.nelit.server.dto.event.EventLocationDTO;
import dev.nelit.server.entity.event.EventLocation;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface EventDataService {
    Mono<Void> upsertEventData(int eventID, Map<String, EventDataDTO> eventData);
}
