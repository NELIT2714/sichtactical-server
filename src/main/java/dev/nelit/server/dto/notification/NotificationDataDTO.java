package dev.nelit.server.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NotificationDataDTO(
    @NotBlank
    @Size(max = 50)
    String title,

    @Size(max = 200)
    String description,

    @Size(max = 65535)
    String content
) {
}
