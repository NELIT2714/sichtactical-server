package dev.nelit.server.entity.event.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_events_rules")
public class EventRule {

    @Id
    @JsonProperty("id_event_rule")
    @Column("id_event_rule")
    private Integer idEventRule;

    @JsonProperty("id_event")
    @Column("id_event")
    private Integer idEvent;

    @JsonProperty("position")
    @Column("position")
    private Integer position;

    public EventRule(Integer idEvent, Integer position) {
        this.idEvent = idEvent;
        this.position = position;
    }

    public Integer getIdEventRule() {
        return idEventRule;
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
