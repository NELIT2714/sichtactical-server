package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EventProgramDTO {

    @JsonProperty("id_event_program")
    private Integer idEventProgram;

    @NotBlank
    @Size(max = 100)
    @JsonProperty("text")
    private String text;

    @Min(0)
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
