package dev.nelit.server.mappers;

import dev.nelit.server.dto.event.EventMemberDataDTO;
import dev.nelit.server.entity.event.EventMember;

public final class EventMemberDataMapper {

    public EventMemberDataMapper() {
    }

    public static EventMemberDataDTO toResponse(EventMember member) {
        EventMemberDataDTO response = new EventMemberDataDTO();
        response.setFullName(member.getFullName());
        response.setCallSign(member.getCallSign());
        response.setPhoneNumber(member.getPhoneNumber());
        response.setEquipment(member.getEquipment());
        response.setRegistered(member.getRegistered());
        response.setRegistrationTimestamp(member.getRegistrationTimestamp());
        response.setUpdateTimestamp(member.getUpdateTimestamp());
        return response;
    }
}
