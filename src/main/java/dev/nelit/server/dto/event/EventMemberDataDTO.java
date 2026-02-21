package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.EquipmentTypes;

import java.time.LocalDateTime;

public class EventMemberDataDTO {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("call_sign")
    private String callSign;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("equipment")
    private EquipmentTypes equipment;

    @JsonProperty("registered")
    private Boolean registered;

    @JsonProperty("registration_timestamp")
    private LocalDateTime registrationTimestamp;

    @JsonProperty("update_timestamp")
    private LocalDateTime updateTimestamp;

    public EventMemberDataDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EquipmentTypes getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentTypes equipment) {
        this.equipment = equipment;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public LocalDateTime getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(LocalDateTime registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
