package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInitRequestDTO(@JsonProperty("user_telegram_id") String userTelegramId) {}
