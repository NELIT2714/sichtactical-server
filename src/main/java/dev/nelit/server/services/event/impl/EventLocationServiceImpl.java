package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventLocationDTO;
import dev.nelit.server.entity.event.EventLocation;
import dev.nelit.server.repositories.event.EventLocationRepository;
import dev.nelit.server.services.event.api.EventLocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
public class EventLocationServiceImpl implements EventLocationService {

    private final EventLocationRepository eventLocationRepository;

    private final TransactionalOperator tx;

    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository, TransactionalOperator tx) {
        this.eventLocationRepository = eventLocationRepository;
        this.tx = tx;
    }

    @Override
    public Mono<EventLocation> upsertLocation(EventLocationDTO dto) {
        if (dto.getIdLocation() != null) {
            return tx.transactional(eventLocationRepository.findById(dto.getIdLocation())
                .flatMap(existing -> {
                    if (dto.getName() != null) existing.setName(dto.getName());
                    if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
                    if (dto.getGoogleMaps() != null) existing.setGoogleMaps(dto.getGoogleMaps());
                    return eventLocationRepository.save(existing);
                })
                .switchIfEmpty(eventLocationRepository.save(new EventLocation(dto.getName(), dto.getAddress(), dto.getGoogleMaps()))));
        }

        return tx.transactional(eventLocationRepository.save(new EventLocation(
            dto.getName(), dto.getAddress(), dto.getGoogleMaps()
        )));
    }
}
