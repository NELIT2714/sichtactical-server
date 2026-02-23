package dev.nelit.server.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageProvider {

    private static final Logger logger = LoggerFactory.getLogger(MessageProvider.class);

    private static final String DEFAULT_LANG = "ru";
    private final Map<String, JsonNode> messages = new HashMap<>();

    public MessageProvider() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:i18/*.json");

        logger.debug("Found {} message files", resources.length);

        for (Resource resource : resources) {
            String filename = resource.getFilename();
            if (filename == null) continue;

            String lang = filename.substring(0, filename.lastIndexOf('.'));
            JsonNode node = objectMapper.readTree(resource.getInputStream());
            messages.put(lang.toLowerCase(), node);
            logger.debug("Loaded message file {}", filename);
        }
    }

    public String get(String lang, String key) {
        if (lang == null) lang = DEFAULT_LANG;

        lang = lang.toLowerCase();
        if (!messages.containsKey(lang)) lang = DEFAULT_LANG;

        JsonNode langNode = messages.get(lang);
        if (langNode == null) return key;

        JsonNode node = langNode.path(key);
        if (node.isMissingNode() || node.isNull()) return key;

        return node.asString();
    }
}
