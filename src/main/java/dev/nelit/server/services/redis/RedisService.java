package dev.nelit.server.services.redis;

import reactor.core.publisher.Mono;

public interface RedisService {
    Mono<Boolean> save(String key, String value);

    Mono<String> get(String key);
}
