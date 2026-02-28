package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserTelegramIdAndLanguageCodeDTO (@JsonProperty("telegram_id") String telegramId, @JsonProperty("language_code") String languageCode) {
}
