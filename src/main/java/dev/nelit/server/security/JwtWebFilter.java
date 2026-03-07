package dev.nelit.server.security;

import dev.nelit.server.services.jwt.JwtServiceImpl;
import dev.nelit.server.services.users.impl.UserServiceImpl;
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

    private final JwtServiceImpl jwtService;
    private final UserServiceImpl userService;

    public JwtWebFilter(JwtServiceImpl jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String jwt = authHeader.substring(7);

        return jwtService.extractSubject(jwt)
            .flatMap(telegramId ->
                userService.getUserResponse(telegramId)
                    .flatMap(user ->
                        jwtService.isTokenValid(jwt, user)
                            .flatMap(valid -> {
                                if (!valid) {
                                    log.warn("Token is invalid for user {}", telegramId);
                                    return chain.filter(exchange);
                                }

                                return jwtService.extractPermissions(jwt)
                                    .flatMap(permissions -> {
                                        List<String> authorities = new ArrayList<>();
                                        authorities.add("ROLE_USER");
                                        permissions.forEach(p -> authorities.add("PERMISSION_" + p));

                                        log.info("User {} authenticated with authorities: {}", telegramId, authorities);

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
                    )
            )
            .onErrorResume(e -> {
                log.error("JWT filter error: {}", e.getMessage(), e);
                return chain.filter(exchange);
            });
    }
}