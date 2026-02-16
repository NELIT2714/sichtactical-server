package dev.nelit.server.services.users.api;

import dev.nelit.server.dto.user.UserUpsertDTO;
import dev.nelit.server.dto.user.UserResponseDTO;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponseDTO> upsertUser(UserUpsertDTO dto);

    Mono<UserResponseDTO> getUserResponse(String telegramID);
}
