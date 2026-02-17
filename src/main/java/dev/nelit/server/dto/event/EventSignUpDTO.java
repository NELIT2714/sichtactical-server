package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.EquipmentTypes;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EventSignUpDTO {

    @NotBlank
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^\\+[0-9]{1,14}$")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank
    @JsonProperty("call_sign")
    private String callSign;

    @NotBlank
    @JsonProperty("equipment_type")
    private EquipmentTypes equipmentType;

    @NotBlank
    @JsonProperty("save_data")
    private Boolean saveData;

    @NotBlank
    @AssertTrue
    @JsonProperty("accept_rules")
    private Boolean acceptRules;

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallSign() {
        return callSign;
    }

    public EquipmentTypes getEquipmentType() {
        return equipmentType;
    }

    public Boolean getSaveData() {
        return saveData;
    }

    public Boolean getAcceptRules() {
        return acceptRules;
    }
}
