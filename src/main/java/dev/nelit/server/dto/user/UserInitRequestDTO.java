package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInitRequestDTO(
    @NotBlank
    @Size(max = 16)
    @JsonProperty("user_telegram_id") String userTelegramId
) {}
