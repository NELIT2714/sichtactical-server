package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventPageResponseDTO;
import dev.nelit.server.dto.event.EventResponseDTO;
import dev.nelit.server.dto.event.EventUpsertDTO;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.event.EventData;
import dev.nelit.server.entity.event.EventLocation;
import dev.nelit.server.entity.event.program.EventProgram;
import dev.nelit.server.entity.event.program.EventProgramI18n;
import dev.nelit.server.entity.event.rule.EventRule;
import dev.nelit.server.entity.event.rule.EventRuleI18n;
import dev.nelit.server.mappers.EventMapper;
import dev.nelit.server.repositories.event.EventDataRepository;
import dev.nelit.server.repositories.event.EventLocationRepository;
import dev.nelit.server.repositories.event.EventRepository;
import dev.nelit.server.repositories.event.program.EventProgramI18nRepository;
import dev.nelit.server.repositories.event.program.EventProgramRepository;
import dev.nelit.server.repositories.event.rule.EventRuleI18nRepository;
import dev.nelit.server.repositories.event.rule.EventRuleRepository;
import dev.nelit.server.services.event.api.EventService;
import dev.nelit.server.services.event.api.EventDataService;
import dev.nelit.server.services.event.api.EventLocationService;
import dev.nelit.server.services.event.api.EventProgramService;
import dev.nelit.server.services.event.api.EventRuleService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;
    private final EventDataRepository eventDataRepository;
    private final EventRuleRepository eventRuleRepository;
    private final EventRuleI18nRepository eventRuleI18nRepository;
    private final EventProgramRepository eventProgramRepository;
    private final EventProgramI18nRepository eventProgramI18nRepository;

    private final EventLocationService eventLocationService;
    private final EventDataService eventDataService;
    private final EventRuleService eventRuleService;
    private final EventProgramService eventProgramService;

    public EventServiceImpl(EventRepository eventRepository,
                            EventLocationRepository eventLocationRepository,
                            EventDataRepository eventDataRepository,
                            EventRuleRepository eventRuleRepository,
                            EventRuleI18nRepository eventRuleI18nRepository,
                            EventProgramRepository eventProgramRepository,
                            EventProgramI18nRepository eventProgramI18nRepository,
                            EventLocationService eventLocationService,
                            EventDataService eventDataService,
                            EventRuleService eventRuleService,
                            EventProgramService eventProgramService) {
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
        this.eventDataRepository = eventDataRepository;
        this.eventRuleRepository = eventRuleRepository;
        this.eventRuleI18nRepository = eventRuleI18nRepository;
        this.eventProgramRepository = eventProgramRepository;
        this.eventProgramI18nRepository = eventProgramI18nRepository;
        this.eventLocationService = eventLocationService;
        this.eventDataService = eventDataService;
        this.eventRuleService = eventRuleService;
        this.eventProgramService = eventProgramService;
    }

    @Override
    public Mono<EventPageResponseDTO> getEvents(int page, int size) {
        int offset = page * size;

        Mono<Long> totalMono = eventRepository.countAll();
        Flux<Event> eventsFlux = eventRepository.findAllPaged(size, offset);

        return totalMono
            .zipWith(eventsFlux.flatMap(event -> getEvent(event.getIdEvent())).collectList())
            .map(tuple -> {
                long totalElements = tuple.getT1();
                List<EventResponseDTO> content = tuple.getT2();

                int totalPages = (int) Math.ceil((double) totalElements / size);

                return new EventPageResponseDTO(content, totalElements, totalPages, page, size);
            });
    }

    @Override
    public Mono<EventResponseDTO> getEvent(int eventID) {
        return eventRepository.findById(eventID)
            .flatMap(event ->
                Mono.zip(
                    eventLocationRepository.findById(event.getIdLocation()),
                    eventDataRepository.findByIdEvent(eventID).collectList(),
                    eventRuleRepository.findByIdEvent(eventID).collectList(),
                    eventProgramRepository.findByIdEvent(eventID).collectList()
                ).flatMap(tuple -> {
                    EventLocation location = tuple.getT1();
                    List<EventData> data = tuple.getT2();
                    List<EventRule> rules = tuple.getT3();
                    List<EventProgram> programs = tuple.getT4();

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
                            event, location, data, rules, i18nTuple.getT1(), programs, i18nTuple.getT2()
                        ));
                })
            );
    }

    @Override
    public Mono<Integer> upsertEvent(EventUpsertDTO dto) {
        return eventLocationService.upsertLocation(dto.getLocation())
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
            });
    }
}
