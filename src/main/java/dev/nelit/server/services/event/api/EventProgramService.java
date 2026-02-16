package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.EventProgramDTO;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface EventProgramService {
    Mono<Void> upsertEventProgram(int eventID, Map<String, List<EventProgramDTO>> program);
}
