package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.AttendanceUpsertDTO;
import dev.nelit.server.entity.event.EventAttendance;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.repositories.event.EventAttendanceRepository;
import dev.nelit.server.repositories.event.EventMemberRepository;
import dev.nelit.server.services.admin.api.AdminService;
import dev.nelit.server.services.event.api.EventAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventAttendanceServiceImpl implements EventAttendanceService {

    private final EventAttendanceRepository attendanceRepository;
    private final AdminService adminService;
    private final EventMemberRepository eventMemberRepository;

    @Override
    public Mono<Void> upsertAttendance(int eventId, AttendanceUpsertDTO dto, int userId) {
        return adminService.findAdminByUserId(userId)
            .flatMap(admin -> eventMemberRepository.findByIdEventMemberAndIdEvent(dto.idEventMember(), eventId)
                .switchIfEmpty(Mono.error(() -> new HTTPException(HttpStatus.NOT_FOUND, "Event member not found")))
                .flatMap(member -> attendanceRepository.findByIdEventAndIdUser(eventId, member.getIdUser())
                    .flatMap(existing -> {
                        existing.setAttended(dto.attended());
                        existing.setMarkedBy(admin.getIdAdmin());
                        existing.setUpdatedAt(LocalDateTime.now());
                        return attendanceRepository.save(existing);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        EventAttendance attendance = EventAttendance.builder()
                            .idEvent(eventId)
                            .idUser(member.getIdUser())
                            .attended(dto.attended())
                            .markedBy(admin.getIdAdmin())
                            .markedAt(LocalDateTime.now())
                            .build();
                        return attendanceRepository.save(attendance);
                    }))
                )
            )
            .then();
    }
}
