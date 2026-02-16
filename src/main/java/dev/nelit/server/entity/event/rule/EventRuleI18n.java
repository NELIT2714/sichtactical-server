package dev.nelit.server.entity.event.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tbl_events_rules_i18n")
public class EventRuleI18n {

    @Id
    @JsonProperty("id_event_rule_i18n")
    @Column("id_event_rule_i18n")
    private Integer idEventRuleI18n;

    @JsonProperty("id_event_rule")
    @Column("id_event_rule")
    private Integer idEventRule;

    @JsonProperty("lang")
    @Column("lang")
    private String lang;

    @JsonProperty("text")
    @Column("text")
    private String text;

    public EventRuleI18n(Integer idEventRuleI18n, Integer idEventRule, String lang, String text) {
        this.idEventRuleI18n = idEventRuleI18n;
        this.idEventRule = idEventRule;
        this.lang = lang;
        this.text = text;
    }

    public Integer getIdEventRuleI18n() {
        return idEventRuleI18n;
    }

    public Integer getIdEventRule() {
        return idEventRule;
    }

    public void setIdEventRule(Integer idEventRule) {
        this.idEventRule = idEventRule;
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
