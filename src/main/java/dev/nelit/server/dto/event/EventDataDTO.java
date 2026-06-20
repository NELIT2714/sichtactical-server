package dev.nelit.server.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EventDataDTO {

    @NotBlank
    @Size(max = 50)
    @JsonProperty("name")
    private String name;

    @Size(max = 65535)
    @JsonProperty("short_description")
    private String shortDescription;

    @Size(max = 65535)
    @JsonProperty("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}