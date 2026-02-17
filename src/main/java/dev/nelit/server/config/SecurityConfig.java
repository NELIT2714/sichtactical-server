package dev.nelit.server.config;

import dev.nelit.server.security.JwtWebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;

import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final CorsProperties corsProperties;
    private final JwtWebFilter jwtWebFilter;

    public SecurityConfig(CorsProperties corsProperties, JwtWebFilter jwtWebFilter) {
        this.corsProperties = corsProperties;
        this.jwtWebFilter = jwtWebFilter;
    }

    @Bean
    public WebFilter loggingFilter() {
        return (exchange, chain) -> {
            String requestID = UUID.randomUUID().toString().substring(0, 5);

            logger.debug("({}) Incoming request: {} {}", requestID, exchange.getRequest().getMethod(), exchange.getRequest().getPath().value());
            logger.debug("({}) Headers: {}", requestID, exchange.getRequest().getHeaders());

            return chain.filter(exchange);
        };
    }

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(corsProperties.getAllowedOrigins());

        logger.debug("CORS origins: {}", corsProperties.getAllowedOrigins());

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .logout(ServerHttpSecurity.LogoutSpec::disable)
            .addFilterAt(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .pathMatchers("/v1/events/**").authenticated()

                .pathMatchers(HttpMethod.POST, "/v1/users/init").permitAll()
                .pathMatchers(HttpMethod.GET,  "/v1/users/me").authenticated()

                .anyExchange().denyAll()
            )
            .build();
    }
}