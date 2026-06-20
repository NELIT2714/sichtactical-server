package dev.nelit.server.controllers.admin;

import dev.nelit.server.dto.ApiResponse;
import dev.nelit.server.dto.event.AttendanceUpsertDTO;
import dev.nelit.server.security.TelegramUserDetails;
import dev.nelit.server.services.event.api.EventAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/admin/events/{event_id}/attendance")
@RequiredArgsConstructor
public class AdminEventAttendanceController {

    private final EventAttendanceService attendanceService;

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<Void>>> upsertAttendance(
        @PathVariable("event_id") int eventId,
        @RequestBody AttendanceUpsertDTO dto,
        @AuthenticationPrincipal TelegramUserDetails user
    ) {
        return attendanceService.upsertAttendance(eventId, dto, user.getUser().getIdUser())
            .thenReturn(ResponseEntity.ok(ApiResponse.ok()));
    }
}
