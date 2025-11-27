package org.realityfn.fortnite.core.models.profiles.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Template
{
    @NotNull
    @NotBlank
    private String templateId;

    private String devName;

    @JsonIgnore
    private List<String> aliases;

    private String sourceAsset;

    @JsonIgnore
    private boolean _private;

    private HashMap<String, Object> attributes = new HashMap<>();

    public Template() {
    }

    public String getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }

    public HashMap<String, Object> getAttributes()
    {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes)
    {
        this.attributes = attributes;
    }

    public String getDevName()
    {
        return devName;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void setDevName(String devName)
    {
        this.devName = devName;
    }

    public String getSourceAsset()
    {
        return sourceAsset;
    }

    public void setSourceAsset(String sourceAsset)
    {
        this.sourceAsset = sourceAsset;
    }

    public boolean is_private() {
        return _private;
    }

    public void set_private(boolean _private) {
        this._private = _private;
    }

    @SuppressWarnings("unchecked")
    public <T> T getStat(String key)
    {
        return attributes.containsKey(key) ? (T) attributes.get(key) : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getStatOrDefault(String key, Object defaultValue)
    {
        return attributes.containsKey(key) ? (T) attributes.get(key) : (T) defaultValue;
    }

    public <T> List<T> getStatlist(String key, Class<T> target)
    {
        ObjectMapper mapper = new ObjectMapper();

        AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();

        mapper.getDeserializationConfig()
                .withInsertedAnnotationIntrospector(introspector);
        mapper.getSerializationConfig()
                .withInsertedAnnotationIntrospector(introspector);

        JavaType type = mapper.getTypeFactory().
                constructCollectionType(
                        ArrayList.class,
                        target);
        try
        {
            return mapper.readValue(mapper.writeValueAsString(attributes.get(key)), type);
        }
        catch (Exception ignored) { ignored.printStackTrace(); }

        return null;
    }

    public <T> T getStat(String name, TypeReference<T> reference)
    {
        Object value = attributes.get(name);

        try
        {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(mapper.writeValueAsString(value), reference);
        }
        catch (Exception ignored) { ignored.printStackTrace(); }

        return null;
    }

    public <T> T getStat(String name, Class<T> type) throws JsonProcessingException
    {
        var stat = attributes.get(name);

        var mapper = new ObjectMapper();

        return mapper.readValue(mapper.writeValueAsString(stat), type);
    }

    public void setStat(String key, Object obj)
    {
        Object objToAdd;

        try
        {
            objToAdd = Document.parse(new ObjectMapper().writeValueAsString(obj));
        }
        catch (Exception ex)
        {
            objToAdd = obj;
        }

        attributes.put(key, objToAdd);
    }

    public String getTemplateProfile() {
        return this.getStat("static_profile_type");
    }
}
