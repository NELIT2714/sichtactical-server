package dev.nelit.server.services.jwt;

import dev.nelit.server.dto.user.UserResponseDTO;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface JwtService {
    Mono<String> generateToken(UserResponseDTO user);

    Mono<String> extractSubject(String token);

    Mono<Set<String>> extractPermissions(String token);

    Mono<Boolean> isTokenValid(String token, UserResponseDTO user);
}
