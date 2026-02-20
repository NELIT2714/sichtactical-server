package dev.nelit.server.controllers;


import dev.nelit.server.dto.user.UserInitRequestDTO;
import dev.nelit.server.dto.user.UserUpsertAppDTO;
import dev.nelit.server.dto.user.UserUpsertDTO;
import dev.nelit.server.security.TelegramUserDetails;
import dev.nelit.server.services.auth.TelegramAuthServiceImpl;
import dev.nelit.server.services.users.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserServiceImpl userService;
    private final TelegramAuthServiceImpl telegramAuthService;

    public UserController(UserServiceImpl userService, TelegramAuthServiceImpl telegramAuthService) {
        this.userService = userService;
        this.telegramAuthService = telegramAuthService;
    }

    @PostMapping(path = "/init")
    public Mono<ResponseEntity<Map<String, Object>>> initUser(
        @RequestHeader("x-telegram-data") String initData,
        @RequestBody UserInitRequestDTO userInitRequestDTO
    ) {
        return telegramAuthService.initUser(initData, userInitRequestDTO.userTelegramId())
            .map(token -> ResponseEntity.ok(
                Map.of(
                    "status", true,
                    "token", token
                )
            ));
    }

    @GetMapping("/me")
    public Mono<ResponseEntity<Map<String, Object>>> getUser(@AuthenticationPrincipal TelegramUserDetails userDetails) {
        return Mono.just(ResponseEntity.ok(
            Map.of(
                "status", true,
                "user", userDetails.getUser()
            )
        ));
    }

    @PostMapping("/bot")
    public Mono<ResponseEntity<Map<String, Object>>> upsertUser(@RequestBody UserUpsertDTO dto) {
        return userService.upsertUser(dto) 
            .map(userResponse -> ResponseEntity.ok(
                Map.of(
                    "status", true,
                    "user", userResponse
                ))
            );
    }

    @PostMapping("/app")
    public Mono<ResponseEntity<Map<String, Object>>> upsertUser(@RequestBody UserUpsertAppDTO dto) {
        return userService.upsertUser(dto)
            .map(userResponse -> ResponseEntity.ok(
                Map.of(
                    "status", true,
                    "user", userResponse
                )
            ));
    }
}
