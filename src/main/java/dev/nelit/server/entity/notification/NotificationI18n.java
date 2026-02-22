package dev.nelit.server.entity.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.NotificationCategories;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_notifications_i18n")
public class NotificationI18n {

    @Id
    @JsonProperty("id_notification_i18n")
    @Column("id_notification_i18n")
    private Integer idNotificationI18n;

    @JsonProperty("id_notification")
    @Column("id_notification")
    private Integer idNotification;

    @JsonProperty("lang")
    @Column("lang")
    private String lang;

    @JsonProperty("title")
    @Column("title")
    private String title;

    @JsonProperty("description")
    @Column("description")
    private String description;

    public NotificationI18n(Integer idNotification, String lang, String title, String description) {
        this.idNotification = idNotification;
        this.lang = lang;
        this.title = title;
        this.description = description;
    }

    public Integer getIdNotificationI18n() {
        return idNotificationI18n;
    }

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
