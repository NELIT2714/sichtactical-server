package dev.nelit.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpsertDTO {

    @NotBlank
    @Size(max = 16)
    @JsonProperty("user_telegram_id")
    private String userTelegramID;

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

    @Size(max = 20)
    @JsonProperty("call_sign")
    private String callSign;

    @NotNull
    @JsonProperty("save_data")
    private Boolean saveData;

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

    public String getCallSign() {
        return callSign;
    }

    public Boolean getSaveData() {
        return saveData;
    }
}