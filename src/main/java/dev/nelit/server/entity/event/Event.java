package dev.nelit.server.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name = "tbl_events")
public class Event {

    @Id
    @JsonProperty("id_event")
    @Column("id_event")
    private Integer idEvent;

    @JsonProperty("event_date")
    @Column("event_date")
    private LocalDate eventDate;

    @JsonProperty("start_time")
    @Column("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @Column("end_time")
    private LocalTime endTime;

    @JsonProperty("max_members")
    @Column("max_members")
    private Integer maxMembers;

    @JsonProperty("id_location")
    @Column("id_location")
    private Integer idLocation;

    @JsonProperty("cost")
    @Column("cost")
    private BigDecimal cost;

    public Event(LocalDate eventDate, LocalTime startTime, LocalTime endTime, Integer maxMembers, Integer idLocation, BigDecimal cost) {
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxMembers = maxMembers;
        this.idLocation = idLocation;
        this.cost = cost;
    }

    public Integer getIdEvent() {
        return idEvent;
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

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
