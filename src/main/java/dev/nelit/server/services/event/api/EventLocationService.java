package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.EventLocationDTO;
import dev.nelit.server.entity.event.EventLocation;
import reactor.core.publisher.Mono;

public interface EventLocationService {
    Mono<EventLocation> upsertLocation(EventLocationDTO dto);
}
