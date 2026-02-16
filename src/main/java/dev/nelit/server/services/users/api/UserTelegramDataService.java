package dev.nelit.server.services.users.api;

import dev.nelit.server.dto.user.UserUpsertDTO;
import dev.nelit.server.entity.user.UserTelegramData;
import reactor.core.publisher.Mono;

public interface UserTelegramDataService {
    Mono<UserTelegramData> upsertTelegramData(UserUpsertDTO dto);

    Mono<UserTelegramData> getUserByTelegramID(String telegramID);

    Mono<Boolean> existsByTelegramId(String telegramID);
}
