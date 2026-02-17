package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.EquipmentTypes;

public class EventSignUpDTO {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("call_sign")
    private String callSign;

    @JsonProperty("equipment_type")
    private EquipmentTypes equipmentType;

    @JsonProperty("save_data")
    private Boolean saveData;

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
