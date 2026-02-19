package dev.nelit.server.controllers;

import dev.nelit.server.dto.event.EventSignUpDTO;
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
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return eventService.getEvents(page, size, user.getUser().getIdUser())
            .map(response -> ResponseEntity.ok(Map.of("status", true, "response", response)));
    }

    @GetMapping("/{eventID}")
    public Mono<ResponseEntity<Map<String, Object>>> getEvent(@AuthenticationPrincipal TelegramUserDetails user, @PathVariable int eventID) {
        return eventService.getEventResponse(eventID, user.getUser().getIdUser())
            .map(response -> ResponseEntity.ok(Map.of("status", true, "event", response)));
    }

    @PostMapping("/{eventID}/members")
    public Mono<ResponseEntity<Map<String, Object>>> signUp(@AuthenticationPrincipal TelegramUserDetails user,
                                                            @PathVariable int eventID,
                                                            @RequestBody EventSignUpDTO dto) {
        return eventService.signUp(eventID, user.getUser().getIdUser(), dto)
            .then(Mono.fromCallable(() -> ResponseEntity.ok(Map.of("status", true))));
    }

    @DeleteMapping("/{eventID}/members")
    public Mono<ResponseEntity<Map<String, Object>>> signOut(@AuthenticationPrincipal TelegramUserDetails user, @PathVariable int eventID) {
        return eventService.signOut(eventID, user.getUser().getIdUser())
            .then(Mono.fromCallable(() -> ResponseEntity.ok(Map.of("status", true))));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> upsertEvent(@RequestBody EventUpsertDTO dto) {
        return eventService.upsertEvent(dto)
            .map(eventID -> ResponseEntity.ok(Map.of("status", true, "event_id", eventID)));
    }
}
