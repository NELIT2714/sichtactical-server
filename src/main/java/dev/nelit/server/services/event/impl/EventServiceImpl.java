package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventPageResponseDTO;
import dev.nelit.server.dto.event.EventResponseDTO;
import dev.nelit.server.dto.event.EventSignUpDTO;
import dev.nelit.server.dto.event.EventUpsertDTO;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.event.EventData;
import dev.nelit.server.entity.event.EventLocation;
import dev.nelit.server.entity.event.program.EventProgram;
import dev.nelit.server.entity.event.program.EventProgramI18n;
import dev.nelit.server.entity.event.rule.EventRule;
import dev.nelit.server.entity.event.rule.EventRuleI18n;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.mappers.EventMapper;
import dev.nelit.server.repositories.event.EventDataRepository;
import dev.nelit.server.repositories.event.EventLocationRepository;
import dev.nelit.server.repositories.event.EventMemberRepository;
import dev.nelit.server.repositories.event.EventRepository;
import dev.nelit.server.repositories.event.program.EventProgramI18nRepository;
import dev.nelit.server.repositories.event.program.EventProgramRepository;
import dev.nelit.server.repositories.event.rule.EventRuleI18nRepository;
import dev.nelit.server.repositories.event.rule.EventRuleRepository;
import dev.nelit.server.services.event.api.*;
import dev.nelit.server.services.users.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Value("${app.timezone:UTC}")
    private String timezone;

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;
    private final EventDataRepository eventDataRepository;
    private final EventRuleRepository eventRuleRepository;
    private final EventRuleI18nRepository eventRuleI18nRepository;
    private final EventProgramRepository eventProgramRepository;
    private final EventProgramI18nRepository eventProgramI18nRepository;
    private final EventMemberRepository eventMemberRepository;

    private final EventLocationService eventLocationService;
    private final EventDataService eventDataService;
    private final EventRuleService eventRuleService;
    private final EventProgramService eventProgramService;
    private final EventMemberServiceImpl eventMemberService;
    private final UserServiceImpl userService;

    private final TransactionalOperator tx;

    public EventServiceImpl(EventRepository eventRepository,
                            EventLocationRepository eventLocationRepository,
                            EventDataRepository eventDataRepository,
                            EventRuleRepository eventRuleRepository,
                            EventRuleI18nRepository eventRuleI18nRepository,
                            EventProgramRepository eventProgramRepository,
                            EventProgramI18nRepository eventProgramI18nRepository, EventMemberRepository eventMemberRepository,
                            EventLocationService eventLocationService,
                            EventDataService eventDataService,
                            EventRuleService eventRuleService,
                            EventProgramService eventProgramService, EventMemberServiceImpl eventMemberService, UserServiceImpl userServiceImpl, TransactionalOperator tx) {
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
        this.eventDataRepository = eventDataRepository;
        this.eventRuleRepository = eventRuleRepository;
        this.eventRuleI18nRepository = eventRuleI18nRepository;
        this.eventProgramRepository = eventProgramRepository;
        this.eventProgramI18nRepository = eventProgramI18nRepository;
        this.eventMemberRepository = eventMemberRepository;
        this.eventLocationService = eventLocationService;
        this.eventDataService = eventDataService;
        this.eventRuleService = eventRuleService;
        this.eventProgramService = eventProgramService;
        this.eventMemberService = eventMemberService;
        this.userService = userServiceImpl;
        this.tx = tx;
    }

    @Override
    public Mono<EventPageResponseDTO> getEvents(int page, int size, Integer userID) {
        int offset = page * size;

        Mono<Long> totalMono = eventRepository.countAll();
        Flux<Event> eventsFlux = eventRepository.findAllPaged(LocalDate.now(ZoneId.of(timezone)), size, offset);

        return totalMono
            .zipWith(eventsFlux.concatMap(event -> getEventResponse(event.getIdEvent(), userID)).collectList())
            .map(tuple -> {
                long totalElements = tuple.getT1();
                List<EventResponseDTO> content = tuple.getT2();

                int totalPages = (int) Math.ceil((double) totalElements / size);

                return new EventPageResponseDTO(content, totalElements, totalPages, page, size);
            });
    }

    @Override
    public Mono<EventResponseDTO> getNearestEvent(int userID) {
        return eventRepository.findNearestUpcoming(LocalDate.now(ZoneId.of(timezone)), LocalTime.now(ZoneId.of(timezone)))
            .flatMap(event -> getEventResponse(event.getIdEvent(), userID));
    }

    @Override
    public Mono<Event> getEvent(int eventID) {
        return eventRepository.findById(eventID).switchIfEmpty(Mono.error(new HTTPException(HttpStatus.NOT_FOUND, "Event not found")));
    }

    @Override
    public Mono<EventResponseDTO> getEventResponse(int eventID, Integer userID) {
        return eventRepository.findById(eventID)
            .flatMap(event ->
                Mono.zip(
                    eventLocationRepository.findById(event.getIdLocation()),
                    eventDataRepository.findByIdEvent(eventID).collectList(),
                    eventRuleRepository.findByIdEvent(eventID).collectList(),
                    eventProgramRepository.findByIdEvent(eventID).collectList(),
                    eventMemberRepository.existsEventMemberByIdEventAndIdUserAndRegisteredIsTrue(eventID, userID).defaultIfEmpty(false),
                    eventMemberRepository.countEventMemberByIdEventAndRegisteredIsTrue(eventID).defaultIfEmpty(0)
                ).flatMap(tuple -> {
                    EventLocation location = tuple.getT1();
                    List<EventData> data = tuple.getT2();
                    List<EventRule> rules = tuple.getT3();
                    List<EventProgram> programs = tuple.getT4();
                    boolean isRegistered = tuple.getT5();
                    int members = tuple.getT6();

                    List<Integer> ruleIds = rules.stream().map(EventRule::getIdEventRule).toList();
                    List<Integer> programIds = programs.stream().map(EventProgram::getIdEventProgram).toList();

                    Mono<List<EventRuleI18n>> rulesI18nMono = ruleIds.isEmpty()
                        ? Mono.just(List.of())
                        : eventRuleI18nRepository.findByIdEventRuleIn(ruleIds).collectList();

                    Mono<List<EventProgramI18n>> programsI18nMono = programIds.isEmpty()
                        ? Mono.just(List.of())
                        : eventProgramI18nRepository.findByIdEventProgramIn(programIds).collectList();

                    return Mono.zip(rulesI18nMono, programsI18nMono)
                        .map(i18nTuple -> EventMapper.toResponseDTO(
                            event, location, data, rules, i18nTuple.getT1(), programs, i18nTuple.getT2(), isRegistered, members
                        ));
                })
            );
    }

    @Override
    public Mono<Void> signUp(int eventID, int userID, EventSignUpDTO eventSignUpDTO) {
        return tx.transactional(getEvent(eventID)
            .flatMap(event -> userService.getUser(userID)
                .flatMap(user -> eventMemberService.signUp(event, user, eventSignUpDTO)))
            .then());
    }

    @Override
    public Mono<Void> signOut(int eventID, int userID) {
        return tx.transactional(eventMemberService.signOut(eventID, userID));
    }

    @Override
    public Mono<Integer> upsertEvent(EventUpsertDTO dto) {
        return tx.transactional(eventLocationService.upsertLocation(dto.getLocation())
            .flatMap(location -> {
                Mono<Event> eventMono;

                if (dto.getIdEvent() != null) {
                    eventMono = eventRepository.findById(dto.getIdEvent())
                        .flatMap(existing -> {
                            if (dto.getEventDate() != null) {
                                existing.setEventDate(dto.getEventDate());
                            }
                            if (dto.getStartTime() != null) {
                                existing.setStartTime(dto.getStartTime());
                            }
                            if (dto.getEndTime() != null) {
                                existing.setEndTime(dto.getEndTime());
                            }
                            if (dto.getMaxMembers() != null) {
                                existing.setMaxMembers(dto.getMaxMembers());
                            }
                            if (location.getIdLocation() != null) {
                                existing.setIdLocation(location.getIdLocation());
                            }
                            if (dto.getCost() != null) {
                                existing.setCost(dto.getCost());
                            }
                            return eventRepository.save(existing);
                        })
                        .switchIfEmpty(Mono.from(eventRepository.save(new Event(
                            dto.getEventDate(),
                            dto.getStartTime(),
                            dto.getEndTime(),
                            dto.getMaxMembers(),
                            location.getIdLocation(),
                            dto.getCost()
                        ))));
                } else {
                    eventMono = eventRepository.save(new Event(
                        dto.getEventDate(),
                        dto.getStartTime(),
                        dto.getEndTime(),
                        dto.getMaxMembers(),
                        location.getIdLocation(),
                        dto.getCost()
                    ));
                }

                return eventMono.flatMap(event ->
                    Mono.when(
                        eventDataService.upsertEventData(event.getIdEvent(), dto.getEventData()),
                        eventRuleService.upsertEventRules(event.getIdEvent(), dto.getEventRules()),
                        eventProgramService.upsertEventProgram(event.getIdEvent(), dto.getEventProgram())
                    ).thenReturn(event.getIdEvent())
                );
            }));
    }
}
