package dev.nelit.server.services.users.impl;

import dev.nelit.server.dto.user.UserUpsertDTO;
import dev.nelit.server.entity.user.UserTelegramData;
import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.repositories.user.UserTelegramDataRepository;
import dev.nelit.server.services.users.api.UserTelegramDataService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
public class UserTelegramDataServiceImpl implements UserTelegramDataService {

    private final UserTelegramDataRepository userTelegramDataRepository;
    private final TransactionalOperator tx;

    public UserTelegramDataServiceImpl(UserTelegramDataRepository userTelegramDataRepository, TransactionalOperator tx) {
        this.userTelegramDataRepository = userTelegramDataRepository;
        this.tx = tx;
    }

    @Override
    public Mono<UserTelegramData> upsertTelegramData(UserUpsertDTO dto) {
        return tx.transactional(userTelegramDataRepository.getUserTelegramDataByTelegramId(dto.getUserTelegramID())
            .flatMap(existing -> {
                boolean changed = false;

                if (dto.getFirstName() != null && !dto.getFirstName().equals(existing.getFirstName())) {
                    existing.setFirstName(dto.getFirstName());
                    changed = true;
                }
                if (dto.getLastName() != null && !dto.getLastName().equals(existing.getLastName())) {
                    existing.setLastName(dto.getLastName().isBlank() ? null : dto.getLastName());
                    changed = true;
                }
                if (dto.getUsername() != null && !dto.getUsername().equals(existing.getUsername())) {
                    existing.setUsername(dto.getUsername().isBlank() ? null : dto.getUsername());
                    changed = true;
                }
                if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().equals(existing.getPhoneNumber())) {
                    existing.setPhoneNumber(dto.getPhoneNumber());
                    changed = true;
                }
//                if (dto.getLanguageCode() != null && existing.getLanguageCode() == null) {
//                    existing.setLanguageCode(dto.getLanguageCode());
//                    changed = true;
//                }
                if (dto.getIsPremium() != null && !dto.getIsPremium().equals(existing.getIsPremium())) {
                    existing.setIsPremium(dto.getIsPremium());
                    changed = true;
                }

                return changed ? userTelegramDataRepository.save(existing) : Mono.just(existing);
            })
            .switchIfEmpty(Mono.defer(() -> {
                if (dto.getFirstName() == null || dto.getLanguageCode() == null) {
                    return Mono.error(new HTTPException(HttpStatus.BAD_REQUEST, "first_name or language_code is blank"));
                }

                return userTelegramDataRepository.save(new UserTelegramData(
                    dto.getUserTelegramID(), dto.getFirstName(), dto.getLastName().isBlank() ? null : dto.getLastName(),
                    dto.getUsername().isBlank() ? null : dto.getUsername(), null, dto.getLanguageCode(), dto.getIsPremium()
                ));
            })));
    }

    @Override
    public Mono<UserTelegramData> getUserByTelegramID(String telegramID) {
        return userTelegramDataRepository.getUserTelegramDataByTelegramId(telegramID)
            .switchIfEmpty(Mono.error(new HTTPException(HttpStatus.NOT_FOUND, "User not found")));
    }
}
