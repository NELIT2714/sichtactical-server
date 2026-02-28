package dev.nelit.server.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.NotificationCategories;

import java.time.LocalDateTime;
import java.util.Map;

public class NotificationResponseDTO {

    @JsonProperty("id_notification")
    private Integer idNotification;

    @JsonProperty("category")
    private NotificationCategories category;

    @JsonProperty("notification_data")
    private Map<String, NotificationDataDTO> notificationData;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public NotificationResponseDTO() {
    }

    public NotificationResponseDTO(Integer idNotification, NotificationCategories category,
                                   LocalDateTime createdAt, LocalDateTime updatedAt,
                                   Map<String, NotificationDataDTO> notificationData) {
        this.idNotification = idNotification;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, NotificationDataDTO> getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(Map<String, NotificationDataDTO> notificationData) {
        this.notificationData = notificationData;
    }
}
