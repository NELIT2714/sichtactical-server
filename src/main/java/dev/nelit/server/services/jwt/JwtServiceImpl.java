package dev.nelit.server.services.jwt;

import dev.nelit.server.dto.user.UserResponseDTO;
import dev.nelit.server.exceptions.HTTPException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token-expiration-minutes:4320}")
    private Integer accessExpiration;

    @Override
    public Mono<String> generateToken(UserResponseDTO user) {
        Instant expiresAt = Instant.now().plus(accessExpiration, ChronoUnit.MINUTES);
        String telegramId = user.getTelegramData().getTelegramId();

        return Mono.just(Jwts.builder()
            .subject(telegramId)
            .claim("user_id", user.getIdUser())
            .claim("telegram_id", telegramId)
            .issuedAt(new Date())
            .expiration(Date.from(expiresAt))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
            .compact());
    }

    @Override
    public Mono<String> extractSubject(String token) {
        return Mono.fromCallable(() -> Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject())
            .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Boolean> isTokenValid(String token, UserResponseDTO user) {
        return Mono.fromCallable(() -> {
            Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

            String subject = claims.getSubject();
            Date expiration = claims.getExpiration();

            return subject != null &&
                subject.equals(user.getTelegramData().getTelegramId()) &&
                !expiration.before(new Date());
        })
        .subscribeOn(Schedulers.boundedElastic())
        .onErrorReturn(false);
    }
}
