package dev.nelit.server.entity.event.program;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_events_programs_i18n")
public class EventProgramI18n {

    @Id
    @JsonProperty("id_event_program_i18n")
    @Column("id_event_program_i18n")
    private Integer idEventProgramI18n;

    @JsonProperty("id_event_program")
    @Column("id_event_program")
    private Integer idEventProgram;

    @JsonProperty("lang")
    @Column("lang")
    private String lang;

    @JsonProperty("text")
    @Column("text")
    private String text;

    public EventProgramI18n() {
    }

    public EventProgramI18n(Integer idEventProgramI18n, Integer idEventProgram, String lang, String text) {
        this.idEventProgramI18n = idEventProgramI18n;
        this.idEventProgram = idEventProgram;
        this.lang = lang;
        this.text = text;
    }

    public Integer getIdEventProgramI18n() {
        return idEventProgramI18n;
    }

    public Integer getIdEventProgram() {
        return idEventProgram;
    }

    public void setIdEventProgram(Integer idEventProgram) {
        this.idEventProgram = idEventProgram;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
