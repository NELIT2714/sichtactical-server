package dev.nelit.server.repositories.user;

import dev.nelit.server.entity.user.UserTelegramData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserTelegramDataRepository extends ReactiveCrudRepository<UserTelegramData, Integer> {
    Mono<UserTelegramData> getUserTelegramDataByTelegramId(String telegramId);

    Mono<Boolean> existsByTelegramId(String telegramId);
}
