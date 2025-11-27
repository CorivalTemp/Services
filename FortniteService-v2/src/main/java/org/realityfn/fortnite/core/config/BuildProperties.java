package org.realityfn.fortnite.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class BuildProperties {

    private static final Properties props = new Properties();

    public static String MCP_VERSION;
    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(java.time.ZoneId.of("UTC"));

    static {
        try (InputStream is = BuildProperties.class.getResourceAsStream("/common.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load common.properties", e);
        }
        InputStream is = BuildProperties.class.getResourceAsStream("/config/version.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectNode mutableNode = jsonNode.deepCopy();
        String tempBranchVersion = String.format("%s-%s", mutableNode.get("branch"), mutableNode.get("version"));
        MCP_VERSION = String.format("%s %s build %s cl %s", props.getProperty("app.intent"), tempBranchVersion, mutableNode.get("build"), mutableNode.get("cln")).replace("\"", "");
    }

    public static final String APP = props.getProperty("config.org.realityfn.fortnite.core.fortnite.app");

    public static Integer getCurrentSeason() {
        return Integer.decode(props.getProperty("config.org.realityfn.fortnite.core.fortnite.season_number"));
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return mapper;
    }
}
