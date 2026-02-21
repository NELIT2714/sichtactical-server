package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.EventPageResponseDTO;
import dev.nelit.server.dto.event.EventResponseDTO;
import dev.nelit.server.dto.event.EventSignUpDTO;
import dev.nelit.server.dto.event.EventUpsertDTO;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.user.User;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface EventService {
    Mono<EventPageResponseDTO> getEvents(int page, int size, Integer userID);

    Mono<EventResponseDTO> getNearestEvent(int userID);

    Mono<Event> getEvent(int eventID);

    Mono<EventResponseDTO> getEventResponse(int eventID, Integer userID);

    Mono<Void> signUp(int eventID, int userID, EventSignUpDTO eventSignUpDTO);

    Mono<Void> signOut(int eventID, int userID);

    Mono<Integer> upsertEvent(EventUpsertDTO dto);

}
