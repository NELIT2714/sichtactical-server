package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class EventUpsertDTO {

    @JsonProperty("id_event")
    private Integer idEvent;

    @NotNull
    @JsonProperty("event_date")
    private LocalDate eventDate;

    @NotNull
    @JsonProperty("start_time")
    private LocalTime startTime;

    @NotNull
    @JsonProperty("end_time")
    private LocalTime endTime;

    @NotNull
    @Min(1)
    @JsonProperty("max_members")
    private Integer maxMembers;

    @NotNull
    @JsonProperty("location")
    private EventLocationDTO location;

    @NotNull
    @DecimalMin(value = "0.0")
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