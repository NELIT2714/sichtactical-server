package dev.nelit.server.controllers;

import dev.nelit.server.dto.event.EventUpsertDTO;
import dev.nelit.server.security.TelegramUserDetails;
import dev.nelit.server.services.event.api.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> getEvents(
        @AuthenticationPrincipal TelegramUserDetails user,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return eventService.getEvents(page - 1, size, user.getUser().getIdUser())
            .map(response -> ResponseEntity.ok(Map.of("status", true, "response", response)));
    }

    @GetMapping("/{event_id}")
    public Mono<ResponseEntity<Map<String, Object>>> getEvent(@AuthenticationPrincipal TelegramUserDetails user, @PathVariable(name = "event_id") int eventID) {
        return eventService.getEventResponse(eventID, user.getUser().getIdUser())
            .map(response -> ResponseEntity.ok(Map.of("status", true, "event", response)));
    }

    @GetMapping("/nearest")
    public Mono<ResponseEntity<Map<String, Object>>> getNearestEvent(@AuthenticationPrincipal TelegramUserDetails user) {
        return eventService.getNearestEvent(user.getUser().getIdUser())
            .map(response -> ResponseEntity.ok(Map.of("status", true, "event", response)));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> upsertEvent(@RequestBody EventUpsertDTO dto) {
        return eventService.upsertEvent(dto)
            .map(eventID -> ResponseEntity.ok(Map.of("status", true, "event_id", eventID)));
    }
}
