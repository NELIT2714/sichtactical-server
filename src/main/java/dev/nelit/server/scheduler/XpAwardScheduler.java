package dev.nelit.server.scheduler;

import dev.nelit.server.repositories.event.EventAttendanceRepository;
import dev.nelit.server.repositories.event.EventRepository;
import dev.nelit.server.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class XpAwardScheduler {

    @Value("${app.xp.event-attendance:150}")
    private int xpPerEvent;

    private final EventRepository eventRepository;
    private final EventAttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Warsaw")
//    @Scheduled(cron = "0 20 21 * * *", zone = "Europe/Warsaw")
    public void awardXpForFinishedEvents() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        log.info("XP award scheduler started for date={}", yesterday);

        eventRepository.findByEventDate(yesterday)
            .flatMap(event -> attendanceRepository.findByIdEventAndAttendedIsTrue(event.getIdEvent())
                .flatMap(attendance -> userRepository.findById(attendance.getIdUser())
                    .switchIfEmpty(Mono.fromRunnable(() ->
                        log.warn("User not found: userId={}", attendance.getIdUser())
                    ))
                    .flatMap(user -> {
                        user.setXpTotal(user.getXpTotal() + xpPerEvent);
                        return userRepository.save(user);
                    })
                    .doOnSuccess(u -> log.debug("XP awarded: userId={} eventId={} xp=+{}", u.getIdUser(), event.getIdEvent(), xpPerEvent))
                    .onErrorResume(e -> {
                        log.error("Failed to award XP: userId={} eventId={}",
                            attendance.getIdUser(), event.getIdEvent(), e);
                        return Mono.empty();
                    })
                )
            )
            .subscribe(
                null,
                e -> log.error("XP award scheduler fatal error", e),
                () -> log.info("XP award scheduler completed for date={}", yesterday)
            );
    }
}
