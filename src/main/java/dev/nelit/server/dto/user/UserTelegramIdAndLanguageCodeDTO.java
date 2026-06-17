package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserTelegramIdAndLanguageCodeDTO(
    @NotBlank
    @Size(max = 16)
    @JsonProperty("telegram_id") String telegramId,

    @NotBlank
    @Size(max = 3, min = 3)
    @JsonProperty("language_code") String languageCode
) {
}
