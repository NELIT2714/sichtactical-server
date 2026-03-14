package dev.nelit.server.services.jwt;

import dev.nelit.server.dto.user.UserResponseDTO;
import dev.nelit.server.enums.AdminPermissions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token-expiration-minutes:4320}")
    private Integer accessExpiration;

    @Override
    public Mono<String> generateToken(UserResponseDTO user) {
        Instant expiresAt = Instant.now().plus(accessExpiration, ChronoUnit.MINUTES);
        String telegramId = user.getTelegramData().getTelegramId();

        Set<String> permissions = Collections.emptySet();
        if (user.getAdminData() != null && user.getAdminData().getPermissions() != null) {
            permissions = user.getAdminData().getPermissions().stream()
                .map(AdminPermissions::name)
                .collect(Collectors.toSet());
        }

        log.info("Generating token for user {}. adminData={}, permissions={}", telegramId, user.getAdminData(), permissions);

        return Mono.just(Jwts.builder()
            .subject(telegramId)
            .claim("user_id", user.getIdUser())
            .claim("telegram_id", telegramId)
            .claim("permissions", permissions)
            .issuedAt(new Date())
            .expiration(Date.from(expiresAt))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
            .compact());
    }

    @Override
    public Mono<String> extractSubject(String token) {
        return Mono.fromCallable(() -> {
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException("Token is null or empty");
            }
            String subject = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
            if (subject == null || subject.isBlank()) {
                throw new IllegalArgumentException("Subject is null or empty in token");
            }
            return subject;
        })
        .subscribeOn(Schedulers.boundedElastic())
        .timeout(Duration.ofSeconds(5));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Set<String>> extractPermissions(String token) {
        return Mono.fromCallable(() -> {
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException("Token is null or empty");
            }
            Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
            Object perms = claims.get("permissions");
            if (perms instanceof List<?> list) {
                return list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .collect(Collectors.toSet());
            }
            return Collections.emptySet();
        }).subscribeOn(Schedulers.boundedElastic())
          .timeout(Duration.ofSeconds(5))
          .map(set -> (Set<String>) set)
          .onErrorResume(e -> Mono.just(Collections.emptySet()));
    }

    @Override
    public Mono<Boolean> isTokenValid(String token, UserResponseDTO user) {
        return Mono.fromCallable(() -> {
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException("Token is null or empty");
            }
            if (user == null || user.getTelegramData() == null) {
                throw new IllegalArgumentException("User or user telegram data is null");
            }
            
            Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

            String subject = claims.getSubject();
            Date expiration = claims.getExpiration();

            return subject != null &&
                subject.equals(user.getTelegramData().getTelegramId()) &&
                expiration != null &&
                !expiration.before(new Date());
        })
        .subscribeOn(Schedulers.boundedElastic())
        .timeout(Duration.ofSeconds(5))
        .onErrorReturn(false);
    }
}
