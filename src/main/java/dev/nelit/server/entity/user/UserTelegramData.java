package dev.nelit.server.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_users_telegram_data")
public class UserTelegramData {

    @Id
    @JsonProperty("id_user_telegram_data")
    @Column("id_user_telegram_data")
    private Integer idUserTelegramData;

    @JsonProperty("telegram_id")
    @Column("telegram_id")
    private String telegramId;

    @JsonProperty("first_name")
    @Column("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Column("last_name")
    private String lastName;

    @JsonProperty("username")
    @Column("username")
    private String username;

    @JsonProperty("phone_number")
    @Column("phone_number")
    private String phoneNumber;

    @JsonProperty("language_code")
    @Column("language_code")
    private String languageCode;

    @JsonProperty("is_premium")
    @Column("is_premium")
    private Boolean isPremium;

    public UserTelegramData(String telegramId, String firstName, String lastName, String username, String phoneNumber, String languageCode, Boolean isPremium) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.languageCode = languageCode;
        this.isPremium = isPremium;
    }

    public Integer getIdUserTelegramData() {
        return idUserTelegramData;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean premium) {
        isPremium = premium;
    }
}
