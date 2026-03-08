package dev.nelit.server.controllers;

import dev.nelit.server.dto.ApiResponse;
import dev.nelit.server.dto.event.EventMemberDataDTO;
import dev.nelit.server.dto.event.EventSignUpDTO;
import dev.nelit.server.security.TelegramUserDetails;
import dev.nelit.server.services.event.api.EventMemberService;
import dev.nelit.server.services.event.api.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/events/{event_id}")
public class EventMemberController {

    private final EventService eventService;
    private final EventMemberService eventMemberService;

    public EventMemberController(EventService eventService, EventMemberService eventMemberService) {
        this.eventService = eventService;
        this.eventMemberService = eventMemberService;
    }

    @GetMapping("/members")
    public Mono<ResponseEntity<ApiResponse<List<EventMemberDataDTO>>>> getMembers(@PathVariable(name = "event_id") int eventID) {
        return eventService.getEvent(eventID)
            .flatMap(eventMemberService::getMembers)
            .map(response -> ResponseEntity.ok(ApiResponse.ok(response)));
    }

    @PostMapping("/members")
    public Mono<ResponseEntity<Map<String, Object>>> signUp(@AuthenticationPrincipal TelegramUserDetails user,
                                                            @PathVariable(name = "event_id") int eventID,
                                                            @RequestBody EventSignUpDTO dto) {
        return eventService.signUp(eventID, user.getUser().getIdUser(), dto)
            .then(Mono.fromCallable(() -> ResponseEntity.ok(Map.of("status", true))));
    }

    @DeleteMapping("/members")
    public Mono<ResponseEntity<Map<String, Object>>> signOut(@AuthenticationPrincipal TelegramUserDetails user, @PathVariable(name = "event_id") int eventID) {
        return eventService.signOut(eventID, user.getUser().getIdUser())
            .then(Mono.fromCallable(() -> ResponseEntity.ok(Map.of("status", true))));
    }
}
