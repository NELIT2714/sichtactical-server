package dev.nelit.server.services.auth;

import dev.nelit.server.exceptions.HTTPException;
import dev.nelit.server.services.jwt.JwtServiceImpl;
import dev.nelit.server.services.users.impl.UserServiceImpl;
import dev.nelit.server.utils.TelegramInitDataVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TelegramAuthServiceImpl implements TelegramAuthService {

    @Value("${bot.token}")
    private String botToken;

    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;

    public TelegramAuthServiceImpl(UserServiceImpl userService, JwtServiceImpl jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Mono<String> initUser(String initData, String userTelegramId) {
        if (!TelegramInitDataVerifier.verify(initData, botToken))
            return Mono.error(new HTTPException(HttpStatus.FORBIDDEN, "Invalid init data"));

        return userService.getUserResponse(userTelegramId)
            .flatMap(user -> jwtService.generateToken(user)
                .flatMap(Mono::just));
    }
}
