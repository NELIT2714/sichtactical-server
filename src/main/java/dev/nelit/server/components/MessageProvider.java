package dev.nelit.server.components;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageProvider {

    private static final Logger logger = LoggerFactory.getLogger(MessageProvider.class);
    private static final String DEFAULT_LANG = "ru";

    private final ObjectMapper objectMapper;
    private final Map<String, JsonNode> messages = new ConcurrentHashMap<>();

    public MessageProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        loadMessages().subscribeOn(Schedulers.boundedElastic()).block();
    }

    private Mono<Void> loadMessages() {
        return Mono.fromCallable(() -> {
                PathMatchingResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();

                Resource[] resources =
                    resolver.getResources("classpath:i18/*.json");

                logger.info("Found {} message files", resources.length);

                for (Resource resource : resources) {
                    String filename = resource.getFilename();
                    if (filename == null) continue;

                    String lang = filename.substring(0,
                        filename.lastIndexOf('.')).toLowerCase();

                    try (InputStream is = resource.getInputStream()) {
                        JsonNode node = objectMapper.readTree(is);
                        messages.put(lang, node);
                        logger.info("Loaded i18n file: {}", filename);
                    }
                }

                return true;
            })
            .then();
    }

    public Mono<String> get(String lang, String key) {
        return get(lang, key, Map.of());
    }

    public Mono<String> get(String lang, String key, Map<String, String> params) {
        final String resolvedLang = resolveLanguage(lang);

        return Mono.fromSupplier(() -> {
            JsonNode langNode = messages.get(resolvedLang);
            if (langNode == null) {
                logger.warn("No messages found for lang {}", resolvedLang);
                return key;
            }

            JsonNode valueNode = resolveKey(langNode, key);
            if (valueNode == null) {
                logger.warn("Missing translation '{}' for lang '{}'", key, resolvedLang);
                return key;
            }

            String value = valueNode.asString();
            return applyParams(value, params);
        });
    }

    private String resolveLanguage(String lang) {
        if (lang == null) return DEFAULT_LANG;

        lang = lang.toLowerCase();
        if (!messages.containsKey(lang)) {
            logger.debug("Language {} not found, fallback to {}", lang, DEFAULT_LANG);
            return DEFAULT_LANG;
        }
        return lang;
    }

    private JsonNode resolveKey(JsonNode root, String key) {
        String[] parts = key.split("\\.");
        JsonNode current = root;

        for (String part : parts) {
            current = current.path(part);
            if (current.isMissingNode() || current.isNull()) {
                return null;
            }
        }
        return current;
    }

    private String applyParams(String template, Map<String, String> params) {
        if (params == null || params.isEmpty()) return template;

        String result = template;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            result = result.replace(placeholder, entry.getValue());
        }
        return result;
    }
}