package dev.nelit.server.controllers.admin;

import dev.nelit.server.security.TelegramUserDetails;
import dev.nelit.server.services.event.api.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
