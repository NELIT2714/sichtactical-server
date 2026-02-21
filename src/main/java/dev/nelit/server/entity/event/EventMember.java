package dev.nelit.server.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nelit.server.enums.EquipmentTypes;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Table(name = "tbl_events_members")
public class EventMember {

    @Id
    @JsonProperty("id_event_member")
    @Column("id_event_member")
    private Integer idEventMember;

    @JsonProperty("id_event")
    @Column("id_event")
    private Integer idEvent;

    @JsonProperty("id_user")
    @Column("id_user")
    private Integer idUser;

    @JsonProperty("full_name")
    @Column("full_name")
    private String fullName;

    @JsonProperty("call_sign")
    @Column("call_sign")
    private String callSign;

    @JsonProperty("phone_number")
    @Column("phone_number")
    private String phoneNumber;

    @JsonProperty("equipment")
    @Column("equipment")
    private EquipmentTypes equipment;

    @JsonProperty("registered")
    @Column("registered")
    private Boolean registered;

    @JsonProperty("registration_timestamp")
    @Column("registration_timestamp")
    private LocalDateTime registrationTimestamp;

    @JsonProperty("update_timestamp")
    @Column("update_timestamp")
    private LocalDateTime updateTimestamp;

    public EventMember(Integer idEvent, Integer idUser, String fullName, String callSign, String phoneNumber, EquipmentTypes equipment) {
        this.idEvent = idEvent;
        this.idUser = idUser;
        this.fullName = fullName;
        this.callSign = callSign;
        this.phoneNumber = phoneNumber;
        this.equipment = equipment;
        this.registrationTimestamp = LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
    }

    public Integer getIdEventMember() {
        return idEventMember;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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
