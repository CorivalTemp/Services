package org.realityfn.fortnite.core.managers.profiles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import org.realityfn.fortnite.core.models.profiles.items.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateManager
{
    private static Logger TemplateLogger = LoggerFactory.getLogger(TemplateManager.class);

    private static Path TEMPLATE_CONFIGURATION_DIRECTORY = null;

    private static List<Template> TEMPLATES = new ArrayList<>();

    public static boolean initialize(String templateFolder) {
        Path resolvedPath;
        try {
            resolvedPath = Paths.get(templateFolder);
        } catch (Exception e) {
            TemplateLogger.error("Failed to parse base path: {}", templateFolder, e);
            return false;
        }

        if (templateFolder.equals("")) {
            try {
                TEMPLATE_CONFIGURATION_DIRECTORY = Paths.get(TemplateManager.class.getClassLoader().getResource(templateFolder).toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        else TEMPLATE_CONFIGURATION_DIRECTORY = resolvedPath;

        if (!Files.exists(TEMPLATE_CONFIGURATION_DIRECTORY))
        {
            TemplateLogger.error("Failed to find template configuration directory at: {}", TEMPLATE_CONFIGURATION_DIRECTORY.toAbsolutePath());
            return false;
        }

        Path itemTemplatesDirectory = Paths.get(TEMPLATE_CONFIGURATION_DIRECTORY.toString(), "templates/ItemTemplates");
        if (!Files.exists(itemTemplatesDirectory))
        {
            TemplateLogger.error("Failed to find item templates directory. {}", itemTemplatesDirectory);
            return false;
        }

        Path dataTemplatesDirectory = Paths.get(TEMPLATE_CONFIGURATION_DIRECTORY.toString(), "templates/DataTemplates");
        if (!Files.exists(dataTemplatesDirectory))
        {
            TemplateLogger.error("Failed to find data templates directory. {}", dataTemplatesDirectory);
            return false;
        }

        if (!loadProfileTemplates())
        {
            TemplateLogger.error("Failed to load profile templates.");
            return false;
        }

        if (loadItemTemplates(itemTemplatesDirectory))
        {
            TemplateLogger.error("Failed to load item templates.");
            return false;
        }

        if (loadDataTemplates(dataTemplatesDirectory))
        {
            TemplateLogger.error("Failed to load data templates.");
            return false;
        }

        return true;
    }

    private static boolean loadProfileTemplates()
    {
        Path path = Paths.get(TEMPLATE_CONFIGURATION_DIRECTORY.toString(), "profiles/Profiles.json");
        if (!Files.exists(path))
        {
            TemplateLogger.error("Failed to find Profiles.json profile template file.");
            return false;
        }
        TemplateLogger.info("Loading profile template file {}", path);

        try
        {
            ObjectMapper mapper = new ObjectMapper();

            List<Template> templates = mapper.readValue(path.toFile(), new TypeReference<>() { });

            TEMPLATES.addAll(templates);
        }
        catch (Exception exception)
        {
            TemplateLogger.error("Failed to read profile template file.", exception);
            return false;
        }

        return true;
    }

    public static List<Template> grabTemplatesByName(String name)
    {
        Path path = Paths.get(TEMPLATE_CONFIGURATION_DIRECTORY.toString(), String.format("templates/ItemTemplates/%s.json", name));
        if (!Files.exists(path))
        {
            TemplateLogger.error("Requested file {}.json was missing!", name);
            return null;
        }

        TemplateLogger.info("Loading template file {} for endpoint call", path);

        try
        {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(path.toFile(), new TypeReference<>() { });
        }
        catch (Exception exception)
        {
            TemplateLogger.error("Failed to read profile template file.", exception);
            return null;
        }
    }

    private static boolean loadItemTemplates(Path templatesDirectory)
    {
        try (Stream<Path> paths = Files.walk(templatesDirectory))
        {
            List<Path> templateFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            for (Path path : templateFiles)
            {
                TemplateLogger.info("Loading template file {}", path);

                try
                {
                    ObjectMapper mapper = new ObjectMapper();

                    List<Template> templates = mapper.readValue(path.toFile(), new TypeReference<>() { });

                    TEMPLATES.addAll(templates);
                }
                catch (Exception exception)
                {
                    throw new RuntimeException(exception);
                }

                if (TEMPLATES.isEmpty())
                {
                    TemplateLogger.warn("No template has been loaded.");
                }
            }
        }
        catch (Exception exception)
        {
            TemplateLogger.error("Failed to list templates directory.", exception);
            return true;
        }

        return false;
    }

    private static boolean loadDataTemplates(Path templatesDirectory)
    {
        try (Stream<Path> paths = Files.walk(templatesDirectory))
        {
            List<Path> templateFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            for (Path path : templateFiles)
            {
                TemplateLogger.info("Loading template file {}", path);
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(path.toFile());

                    if (root.isArray()) {
                        List<Template> templates = mapper.readValue(
                                path.toFile(),
                                new TypeReference<>() {
                                }
                        );
                        TEMPLATES.addAll(templates);
                    } else if (root.isObject()) {
                        Template template = mapper.readValue(
                                path.toFile(),
                                Template.class
                        );
                        TEMPLATES.add(template);
                    } else {
                        System.err.println("Unexpected JSON structure: " + path);
                    }
                }
                catch (Exception exception)
                {
                    throw new RuntimeException(exception);
                }

                if (TEMPLATES.isEmpty())
                {
                    TemplateLogger.warn("No template has been loaded.");
                }
            }
        }
        catch (Exception exception)
        {
            TemplateLogger.error("Failed to list templates directory.", exception);
            return true;
        }

        return false;
    }

    public static List<Template> getTemplates()
    {
        return TEMPLATES;
    }

    public static Template getTemplate(String templateId)
    {
        for (Template template : TEMPLATES)
        {
            if (template.getTemplateId().equals(templateId))
            {
                return template;
            }
        }

        return null;
    }

    public static Template findProfileTemplateByType(String profileId)
    {
        for (Template template : TEMPLATES)
        {
            if (template.getTemplateId().startsWith("player:"))
            {
                String profileType = template.getStat("static_profile_type");

                if (!StringUtils.isBlank(profileType) && profileType.equals(profileId))
                {
                    return template;
                }
            }
        }
        return null;
    }

    public static ArrayList<Template> findAllProfileTemplates()
    {
        ArrayList<Template> profileTemplates = new ArrayList<>();
        for (Template template : TEMPLATES)
        {
            if (template.getTemplateId().startsWith("player:"))
            {
                profileTemplates.add(template);
            }
        }
        return profileTemplates;
    }
}