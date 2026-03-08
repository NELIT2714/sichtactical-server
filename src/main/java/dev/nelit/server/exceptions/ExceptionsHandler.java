package dev.nelit.server.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(HTTPException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleException(HTTPException ex) {
        return Mono.just(
            ResponseEntity.status(ex.getStatus()).body(Map.of(
                "status", false, "message", ex.getMessage()
            ))
        );
    }

}
