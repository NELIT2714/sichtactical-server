package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UserUpsertDTO {

    @NotBlank
    @JsonProperty("user_telegram_id")
    private String userTelegramID;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("language_code")
    private String languageCode;

    @JsonProperty("is_premium")
    private Boolean isPremium;

    @JsonProperty("call_sign")
    private Boolean callSign;

    public String getUserTelegramID() {
        return userTelegramID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public Boolean getCallSign() {
        return callSign;
    }
}
