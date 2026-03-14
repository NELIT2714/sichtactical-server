package dev.nelit.server.security;

import dev.nelit.server.services.jwt.JwtServiceImpl;
import dev.nelit.server.services.users.impl.UserServiceImpl;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtWebFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtWebFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    private final JwtServiceImpl jwtService;
    private final UserServiceImpl userService;

    public JwtWebFilter(JwtServiceImpl jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return chain.filter(exchange);
        }

        String jwt = authHeader.substring(BEARER_PREFIX_LENGTH);

        if (jwt.isBlank()) {
            log.warn("Empty JWT token received");
            return chain.filter(exchange);
        }

        return processJwt(jwt, exchange, chain)
            .onErrorResume(JwtException.class, e -> {
                log.warn("Invalid JWT token: {}", e.getClass().getSimpleName());
                return chain.filter(exchange);
            })
            .onErrorResume(IllegalArgumentException.class, e -> {
                log.warn("JWT validation error: {}", e.getMessage());
                return chain.filter(exchange);
            })
            .onErrorResume(e -> {
                log.error("Unexpected error in JWT filter: {}", e.getClass().getSimpleName());
                return chain.filter(exchange);
            });
    }

    private Mono<Void> processJwt(String jwt, ServerWebExchange exchange, WebFilterChain chain) {
        return jwtService.extractSubject(jwt)
            .flatMap(telegramId -> {
                if (telegramId.isBlank()) {
                    return Mono.error(new JwtException("Invalid subject in token"));
                }

                return userService.getUserResponse(telegramId)
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found: " + telegramId)))
                    .flatMap(user ->
                        jwtService.isTokenValid(jwt, user)
                            .flatMap(valid -> {
                                if (!valid) {
                                    log.warn("Token validation failed for user {}", telegramId);
                                    return Mono.error(new JwtException("Token is invalid"));
                                }

                                return jwtService.extractPermissions(jwt)
                                    .flatMap(permissions -> {
                                        List<String> authorities = new ArrayList<>();
                                        authorities.add("ROLE_USER");
                                        permissions.forEach(p -> authorities.add("PERMISSION_" + p));

                                        log.info("User {} authenticated successfully with {} permissions",
                                            telegramId, permissions.size());

                                        var userDetails = new TelegramUserDetails(user, authorities);

                                        var authentication =
                                            new UsernamePasswordAuthenticationToken(
                                                userDetails,
                                                null,
                                                userDetails.getAuthorities()
                                            );

                                        return chain.filter(exchange)
                                            .contextWrite(
                                                ReactiveSecurityContextHolder.withAuthentication(authentication)
                                            );
                                    });
                            })
                    );
            });
    }
}