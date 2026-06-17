package dev.nelit.server.controllers.admin;

import dev.nelit.server.dto.ApiResponse;
import dev.nelit.server.dto.event.EventUpsertDTO;
import dev.nelit.server.security.TelegramUserDetails;
import dev.nelit.server.services.event.api.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> getEvents(
        @AuthenticationPrincipal TelegramUserDetails user,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return eventService.getAdminEvents(page - 1, size, user.getUser().getIdUser())
            .map(response -> ResponseEntity.ok(Map.of("status", true, "response", response)));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> upsertEvent(@RequestBody EventUpsertDTO dto) {
        return eventService.upsertEvent(dto)
            .map(eventID -> ResponseEntity.ok(Map.of("status", true, "event_id", eventID)));
    }

    @DeleteMapping("/{event_id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteEvent(@PathVariable(name = "event_id") int eventID) {
        return eventService.removeEvent(eventID)
            .thenReturn(ResponseEntity.ok(ApiResponse.ok()));
    }
}
