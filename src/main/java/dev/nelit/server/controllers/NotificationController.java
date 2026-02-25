package dev.nelit.server.controllers;

import dev.nelit.server.security.TelegramUserDetails;
import dev.nelit.server.services.notification.api.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> getAllNotifications(
        @AuthenticationPrincipal TelegramUserDetails user,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return notificationService.getNotifications(page - 1, size)
            .map(response -> ResponseEntity.ok(Map.of("status", true, "response", response)));
    }
}
