package dev.nelit.server.mappers;

import dev.nelit.server.dto.event.*;
import dev.nelit.server.entity.event.Event;
import dev.nelit.server.entity.event.EventData;
import dev.nelit.server.entity.event.EventLocation;
import dev.nelit.server.entity.event.program.EventProgram;
import dev.nelit.server.entity.event.program.EventProgramI18n;
import dev.nelit.server.entity.event.rule.EventRule;
import dev.nelit.server.entity.event.rule.EventRuleI18n;

import java.util.*;
import java.util.stream.Collectors;

public final class EventMapper {

    private EventMapper() {}

    public static EventResponseDTO toResponseDTO(Event event, EventLocation location, List<EventData> data,
                                                 List<EventRule> rules, List<EventRuleI18n> rulesI18n,
                                                 List<EventProgram> programs, List<EventProgramI18n> programsI18n,
                                                 boolean isRegistered, int members) {
        Set<String> languages = data.stream().map(EventData::getLang).collect(Collectors.toSet());

        EventResponseDTO response = new EventResponseDTO();
        response.setIdEvent(event.getIdEvent());
        response.setEventDate(event.getEventDate());
        response.setStartTime(event.getStartTime());
        response.setEndTime(event.getEndTime());
        response.setMaxMembers(event.getMaxMembers());
        response.setLocation(getLocationResponse(location));
        response.setCost(event.getCost());
        response.setEventData(getDataResponse(data));
        response.setEventRules(getRulesResponse(rules, rulesI18n, languages));
        response.setEventProgram(getProgramResponse(programs, programsI18n, languages));
        response.setRegistered(isRegistered);
        response.setMembers(members);
        return response;
    }

    private static EventLocationDTO getLocationResponse(EventLocation location) {
        EventLocationDTO response = new EventLocationDTO();
        response.setIdLocation(location.getIdLocation());
        response.setName(location.getName());
        response.setAddress(location.getAddress());
        response.setGoogleMaps(location.getGoogleMaps());
        return response;
    }

    private static Map<String, EventDataDTO> getDataResponse(List<EventData> data) {
        return data.stream()
            .collect(Collectors.toMap(
                EventData::getLang,
                d -> {
                    EventDataDTO dto = new EventDataDTO();
                    dto.setName(d.getName());
                    dto.setShortDescription(d.getShortDescription());
                    dto.setDescription(d.getDescription());
                    return dto;
                }
            ));
    }

    private static Map<String, List<EventRuleDTO>> getRulesResponse(List<EventRule> rules, List<EventRuleI18n> rulesI18n, Set<String> languages) {
        Map<Integer, Map<String, String>> i18nMap = new HashMap<>();

        for (EventRuleI18n i18n : rulesI18n) {
            i18nMap.computeIfAbsent(i18n.getIdEventRule(), k -> new HashMap<>()).put(i18n.getLang(), i18n.getText());
        }

        List<EventRule> sortedRules = new ArrayList<>(rules);
        sortedRules.sort(Comparator.comparing(EventRule::getPosition));

        Map<String, List<EventRuleDTO>> result = new HashMap<>(languages.size());

        for (String lang : languages) {
            List<EventRuleDTO> langRules = new ArrayList<>(sortedRules.size());

            for (EventRule rule : sortedRules) {
                EventRuleDTO dto = new EventRuleDTO();
                dto.setIdEventRule(rule.getIdEventRule());
                dto.setPosition(rule.getPosition());

                Map<String, String> translations = i18nMap.get(rule.getIdEventRule());
                if (translations != null) {
                    dto.setText(translations.get(lang));
                }

                langRules.add(dto);
            }

            result.put(lang, langRules);
        }

        return result;
    }

    private static Map<String, List<EventProgramDTO>> getProgramResponse(List<EventProgram> programs, List<EventProgramI18n> programsI18n, Set<String> languages) {
        Map<Integer, Map<String, String>> i18nMap = new HashMap<>();

        for (EventProgramI18n i18n : programsI18n) {
            i18nMap.computeIfAbsent(i18n.getIdEventProgram(), k -> new HashMap<>()).put(i18n.getLang(), i18n.getText());
        }

        List<EventProgram> sortedPrograms = new ArrayList<>(programs);
        sortedPrograms.sort(Comparator.comparing(EventProgram::getPosition));

        Map<String, List<EventProgramDTO>> result = new HashMap<>(languages.size());

        for (String lang : languages) {
            List<EventProgramDTO> langPrograms = new ArrayList<>(sortedPrograms.size());

            for (EventProgram program : sortedPrograms) {
                EventProgramDTO dto = new EventProgramDTO();
                dto.setIdEventProgram(program.getIdEventProgram());
                dto.setPosition(program.getPosition());

                Map<String, String> translations = i18nMap.get(program.getIdEventProgram());
                if (translations != null) {
                    dto.setText(translations.get(lang));
                }

                langPrograms.add(dto);
            }

            result.put(lang, langPrograms);
        }

        return result;
    }
}
