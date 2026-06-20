package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.EquipmentTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class EventMemberDataDTO {

    @JsonProperty("id_event_member")
    private Integer idEventMember;

    @NotBlank
    @Size(max = 150)
    @JsonProperty("full_name")
    private String fullName;

    @Size(max = 20)
    @JsonProperty("call_sign")
    private String callSign;

    @NotBlank
    @Size(max = 16)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotNull
    @JsonProperty("equipment")
    private EquipmentTypes equipment;

    @NotNull
    @JsonProperty("registered")
    private Boolean registered;

    @JsonProperty("attended")
    private Boolean attended;

    @NotNull
    @JsonProperty("registration_timestamp")
    private LocalDateTime registrationTimestamp;

    @JsonProperty("update_timestamp")
    private LocalDateTime updateTimestamp;

    public EventMemberDataDTO() {
    }

    public Integer getIdEventMember() {
        return idEventMember;
    }

    public void setIdEventMember(Integer idEventMember) {
        this.idEventMember = idEventMember;
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

    public Boolean getAttended() {
        return attended;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
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
