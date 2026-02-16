package dev.nelit.server.services.auth;

import reactor.core.publisher.Mono;

public interface TelegramAuthService {
    Mono<String> initUser(String initData, String userTelegramId);
}
