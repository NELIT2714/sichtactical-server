package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventProgramDTO {

    @JsonProperty("id_event_program")
    private Integer idEventProgram;

    @JsonProperty("text")
    private String text;

    @JsonProperty("position")
    private Integer position;

    public Integer getIdEventProgram() {
        return idEventProgram;
    }

    public void setIdEventProgram(Integer idEventProgram) {
        this.idEventProgram = idEventProgram;
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
