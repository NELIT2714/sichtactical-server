package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AttendanceUpsertDTO(
    @JsonProperty("id_event_member") Integer idEventMember,
    @JsonProperty("attended") Boolean attended
) {}