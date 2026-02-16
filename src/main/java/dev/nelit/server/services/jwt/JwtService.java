package dev.nelit.server.services.jwt;

import dev.nelit.server.dto.user.UserResponseDTO;
import reactor.core.publisher.Mono;

public interface JwtService {
    Mono<String> generateToken(UserResponseDTO user);

    Mono<String> extractSubject(String token);

    Mono<Boolean> isTokenValid(String token, UserResponseDTO user);
}
