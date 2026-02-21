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
        response.setPhoneNumber(member.getCallSign());
        response.setEquipment(member.getEquipment());
        response.setRegistrationTimestamp(member.getRegistrationTimestamp());
        return response;
    }
}
