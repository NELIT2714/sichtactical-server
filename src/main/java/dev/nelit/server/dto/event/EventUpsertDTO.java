package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class EventUpsertDTO {

    @JsonProperty("id_event")
    private Integer idEvent;

    @JsonProperty("event_date")
    private LocalDate eventDate;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("max_members")
    private Integer maxMembers;

    @JsonProperty("location")
    private EventLocationDTO location;

    @JsonProperty("cost")
    private BigDecimal cost;

    @JsonProperty("event_data")
    private Map<String, EventDataDTO> eventData;

    @JsonProperty("event_rules")
    private Map<String, List<EventRuleDTO>> eventRules;

    @JsonProperty("event_program")
    private Map<String, List<EventProgramDTO>> eventProgram;

    public Integer getIdEvent() {
        return idEvent;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

    public EventLocationDTO getLocation() {
        return location;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Map<String, EventDataDTO> getEventData() {
        return eventData;
    }

    public Map<String, List<EventRuleDTO>> getEventRules() {
        return eventRules;
    }

    public Map<String, List<EventProgramDTO>> getEventProgram() {
        return eventProgram;
    }
}
