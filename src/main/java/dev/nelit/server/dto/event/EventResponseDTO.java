package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class EventResponseDTO {

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

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }

    public EventLocationDTO getLocation() {
        return location;
    }

    public void setLocation(EventLocationDTO location) {
        this.location = location;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Map<String, EventDataDTO> getEventData() {
        return eventData;
    }

    public void setEventData(Map<String, EventDataDTO> eventData) {
        this.eventData = eventData;
    }

    public Map<String, List<EventRuleDTO>> getEventRules() {
        return eventRules;
    }

    public void setEventRules(Map<String, List<EventRuleDTO>> eventRules) {
        this.eventRules = eventRules;
    }

    public Map<String, List<EventProgramDTO>> getEventProgram() {
        return eventProgram;
    }

    public void setEventProgram(Map<String, List<EventProgramDTO>> eventProgram) {
        this.eventProgram = eventProgram;
    }
}
