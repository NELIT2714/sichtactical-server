package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventRuleDTO {

    @JsonProperty("id_event_rule")
    private Integer idEventRule;

    @JsonProperty("text")
    private String text;

    @JsonProperty("position")
    private Integer position;

    public Integer getIdEventRule() {
        return idEventRule;
    }

    public void setIdEventRule(Integer idEventRule) {
        this.idEventRule = idEventRule;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
