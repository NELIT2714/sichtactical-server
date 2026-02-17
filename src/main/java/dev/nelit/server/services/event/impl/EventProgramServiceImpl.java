package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventProgramDTO;
import dev.nelit.server.entity.event.program.EventProgram;
import dev.nelit.server.entity.event.program.EventProgramI18n;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.repositories.event.program.EventProgramI18nRepository;
import dev.nelit.server.repositories.event.program.EventProgramRepository;
import dev.nelit.server.services.event.api.EventProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EventProgramServiceImpl implements EventProgramService {

    private final EventProgramRepository eventProgramRepository;
    private final EventProgramI18nRepository eventProgramI18nRepository;

    private final TransactionalOperator tx;

    public EventProgramServiceImpl(EventProgramRepository eventProgramRepository, EventProgramI18nRepository eventProgramI18nRepository, TransactionalOperator tx) {
        this.eventProgramRepository = eventProgramRepository;
        this.eventProgramI18nRepository = eventProgramI18nRepository;
        this.tx = tx;
    }

    @Override
    public Mono<Void> upsertEventProgram(int eventID, Map<String, List<EventProgramDTO>> program) {
        if (program == null || program.isEmpty()) return Mono.empty();

        String firstLang = program.keySet().iterator().next();
        List<EventProgramDTO> structure = program.get(firstLang);

        return tx.transactional(Flux.fromIterable(structure)
            .flatMap(dto -> {
                Mono<EventProgram> programMono;

                if (dto.getIdEventProgram() != null) {
                    programMono = eventProgramRepository.findById(dto.getIdEventProgram())
                        .flatMap(existing -> {
                            if (!existing.getIdEvent().equals(eventID)) {
                                return Mono.error(new HTTPException(HttpStatus.CONFLICT, "Program item belongs to different event"));
                            }
                            existing.setPosition(dto.getPosition());
                            return eventProgramRepository.save(existing);
                        })
                        .switchIfEmpty(Mono.error(new HTTPException(HttpStatus.NOT_FOUND, "Program item with id " + dto.getIdEventProgram() + " not found")));
                } else {
                    programMono = eventProgramRepository.save(new EventProgram(eventID, dto.getPosition()));
                }

                return programMono.flatMap(savedProgram -> eventProgramI18nRepository.deleteByIdEventProgram(savedProgram.getIdEventProgram())
                    .thenMany(
                        Flux.fromIterable(program.entrySet())
                            .flatMap(entry -> {
                                String lang = entry.getKey();
                                EventProgramDTO langDto = entry.getValue().stream()
                                    .filter(d -> Objects.equals(d.getPosition(), dto.getPosition()))
                                    .findFirst()
                                    .orElse(null);

                                if (langDto != null && langDto.getText() != null && !langDto.getText().isBlank()) {
                                    return eventProgramI18nRepository.save(
                                        new EventProgramI18n(null, savedProgram.getIdEventProgram(), lang, langDto.getText())
                                    );
                                }
                                return Mono.empty();
                            })
                    )
                    .then());
            })
            .then());
    }
}
