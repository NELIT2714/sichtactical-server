package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.EventPageResponseDTO;
import dev.nelit.server.dto.event.EventResponseDTO;
import dev.nelit.server.dto.event.EventUpsertDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
    Mono<EventPageResponseDTO> getEvents(int page, int size);

    Mono<EventResponseDTO> getEvent(int eventID);

    Mono<Integer> upsertEvent(EventUpsertDTO dto);
}
