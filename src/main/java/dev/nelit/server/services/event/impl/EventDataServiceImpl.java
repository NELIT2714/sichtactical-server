package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventDataDTO;
import dev.nelit.server.entity.event.EventData;
import dev.nelit.server.repositories.event.EventDataRepository;
import dev.nelit.server.services.event.api.EventDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class EventDataServiceImpl implements EventDataService {

    private final EventDataRepository eventDataRepository;

    private final TransactionalOperator tx;

    public EventDataServiceImpl(EventDataRepository eventDataRepository, TransactionalOperator tx) {
        this.eventDataRepository = eventDataRepository;
        this.tx = tx;
    }

    @Override
    public Mono<Void> upsertEventData(int eventID, Map<String, EventDataDTO> eventData) {
        if (eventData == null) return Mono.empty();

        return tx.transactional(eventDataRepository.deleteByIdEvent(eventID)
            .thenMany(Flux.fromIterable(eventData.entrySet())
                .flatMap(entry -> {
                    String lang = entry.getKey();
                    EventDataDTO dto = entry.getValue();

                    return Mono.from(eventDataRepository.save(new EventData(
                        eventID, lang, dto.getName(), dto.getShortDescription(), dto.getDescription()
                    )));
                }))
            .then());
    }
}
