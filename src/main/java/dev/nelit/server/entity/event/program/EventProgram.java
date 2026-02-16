package dev.nelit.server.entity.event.program;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_events_programs")
public class EventProgram {

    @Id
    @JsonProperty("id_event_program")
    @Column("id_event_program")
    private Integer idEventProgram;

    @JsonProperty("id_event")
    @Column("id_event")
    private Integer idEvent;

    @JsonProperty("position")
    @Column("position")
    private Integer position;

    public EventProgram(Integer idEvent, Integer position) {
        this.idEvent = idEvent;
        this.position = position;
    }

    public Integer getIdEventProgram() {
        return idEventProgram;
    }

    public void setIdEventProgram(Integer idEventProgram) {
        this.idEventProgram = idEventProgram;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
