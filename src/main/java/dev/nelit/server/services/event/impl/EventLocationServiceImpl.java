package dev.nelit.server.services.event.impl;

import dev.nelit.server.dto.event.EventLocationDTO;
import dev.nelit.server.entity.event.EventLocation;
import dev.nelit.server.repositories.event.EventLocationRepository;
import dev.nelit.server.services.event.api.EventLocationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventLocationServiceImpl implements EventLocationService {

    private final EventLocationRepository eventLocationRepository;

    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository) {
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public Mono<EventLocation> upsertLocation(EventLocationDTO dto) {
        if (dto == null) return Mono.empty();

        if (dto.getIdLocation() != null) {
            return eventLocationRepository.findById(dto.getIdLocation())
                .flatMap(existing -> {
                    if (dto.getName() != null) existing.setName(dto.getName());
                    if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
                    if (dto.getGoogleMaps() != null) existing.setGoogleMaps(dto.getGoogleMaps());
                    return eventLocationRepository.save(existing);
                })
                .switchIfEmpty(eventLocationRepository.save(new EventLocation(dto.getName(), dto.getAddress(), dto.getGoogleMaps())));
        }

        return eventLocationRepository.save(new EventLocation(
            dto.getName(), dto.getAddress(), dto.getGoogleMaps()
        ));
    }
}
