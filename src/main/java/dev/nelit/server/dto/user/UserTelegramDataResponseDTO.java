package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserTelegramDataResponseDTO {

    @JsonProperty("id_user_telegram_data")
    private Integer idUserTelegramData;

    @NotBlank
    @Size(max = 16)
    @JsonProperty("telegram_id")
    private String telegramId;

    @NotBlank
    @Size(max = 64)
    @JsonProperty("first_name")
    private String firstName;

    @Size(max = 64)
    @JsonProperty("last_name")
    private String lastName;

    @Size(max = 32)
    @JsonProperty("username")
    private String username;

    @Size(max = 16)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank
    @Size(max = 3, min = 3)
    @JsonProperty("language_code")
    private String languageCode;

    @NotNull
    @JsonProperty("is_premium")
    private Boolean isPremium;

    public UserTelegramDataResponseDTO() {
    }

    public Integer getIdUserTelegramData() {
        return idUserTelegramData;
    }

    public void setIdUserTelegramData(Integer idUserTelegramData) {
        this.idUserTelegramData = idUserTelegramData;
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

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }
}
