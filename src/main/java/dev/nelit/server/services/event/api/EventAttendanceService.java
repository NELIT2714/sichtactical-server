package dev.nelit.server.services.event.api;

import dev.nelit.server.dto.event.AttendanceUpsertDTO;
import reactor.core.publisher.Mono;

public interface EventAttendanceService {
    Mono<Void> upsertAttendance(int eventId, AttendanceUpsertDTO dto, int adminId);
}
