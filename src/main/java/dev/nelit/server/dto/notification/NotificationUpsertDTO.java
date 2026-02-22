package dev.nelit.server.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.NotificationCategories;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Map;

public class NotificationUpsertDTO {

    @JsonProperty("id_notification")
    private Integer idNotification;

    @JsonProperty("category")
    @NotBlank
    private NotificationCategories category;

    @JsonProperty("notification_data")
    @NotBlank
    private Map<String, NotificationDataDTO> notificationData;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public NotificationUpsertDTO(Integer idNotification, NotificationCategories category, Map<String, NotificationDataDTO> notificationData) {
        this.idNotification = idNotification;
        this.category = category;
        this.notificationData = notificationData;
    }

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public NotificationCategories getCategory() {
        return category;
    }

    public void setCategory(NotificationCategories category) {
        this.category = category;
    }

    public Map<String, NotificationDataDTO> getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(Map<String, NotificationDataDTO> notificationData) {
        this.notificationData = notificationData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
