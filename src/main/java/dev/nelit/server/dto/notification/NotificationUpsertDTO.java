package dev.nelit.server.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.NotificationCategories;

import java.util.Map;

public class NotificationUpsertDTO {

    @JsonProperty("id_notification")
    private Integer idNotification;

    @JsonProperty("category")
    private NotificationCategories category;

    @JsonProperty("notification_data")
    private Map<String, NotificationDataDTO> notificationData;

    public NotificationUpsertDTO(NotificationCategories category, Map<String, NotificationDataDTO> notificationData) {
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
}
