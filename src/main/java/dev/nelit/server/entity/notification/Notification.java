package dev.nelit.server.entity.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.NotificationCategories;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "tbl_notifications")
public class Notification {

    @Id
    @JsonProperty("id_notification")
    @Column("id_notification")
    private String idNotification;

    @JsonProperty("category")
    @Column("category")
    private NotificationCategories category;

    @JsonProperty("created_at")
    @Column("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column("updated_at")
    private LocalDateTime updatedAt;

    public Notification(NotificationCategories category, LocalDateTime createdAt) {
        this.category = category;
        this.createdAt = createdAt;
    }

    public String getIdNotification() {
        return idNotification;
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
}
