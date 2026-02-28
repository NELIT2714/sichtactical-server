package dev.nelit.server.mappers;

import dev.nelit.server.dto.notification.NotificationDataDTO;
import dev.nelit.server.dto.notification.NotificationResponseDTO;
import dev.nelit.server.entity.notification.Notification;
import dev.nelit.server.entity.notification.NotificationI18n;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class NotificationMapper {

    private NotificationMapper() {}

    public static NotificationResponseDTO toResponseDTO(Notification notification, List<NotificationI18n> i18nList) {
        return new NotificationResponseDTO(
            notification.getIdNotification(),
            notification.getCategory(),
            notification.getCreatedAt(),
            notification.getUpdatedAt(),
            toI18nMap(i18nList)
        );
    }

    private static Map<String, NotificationDataDTO> toI18nMap(List<NotificationI18n> i18nList) {
        return i18nList.stream()
            .collect(Collectors.toMap(
                NotificationI18n::getLang,
                i18n -> new NotificationDataDTO(
                    i18n.getTitle(),
                    i18n.getDescription(),
                    i18n.getContent()
                )
            ));
    }
}

