package dev.nelit.server.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_events_data")
public class EventData {

    @Id
    @JsonProperty("id_event_data")
    @Column("id_event_data")
    private Integer idEventData;

    @JsonProperty("id_event")
    @Column("id_event")
    private Integer idEvent;

    @JsonProperty("lang")
    @Column("lang")
    private String lang;

    @JsonProperty("name")
    @Column("name")
    private String name;

    @JsonProperty("short_description")
    @Column("short_description")
    private String shortDescription;

    @JsonProperty("description")
    @Column("description")
    private String description;

    public EventData(Integer idEvent, String lang, String name, String shortDescription, String description) {
        this.idEvent = idEvent;
        this.lang = lang;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
    }

    public Integer getIdEventData() {
        return idEventData;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

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
