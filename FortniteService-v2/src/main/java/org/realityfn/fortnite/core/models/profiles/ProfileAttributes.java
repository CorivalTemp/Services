package org.realityfn.fortnite.core.models.profiles;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

public class ProfileAttributes {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public HashMap<String, Object> attributes;

    public ProfileAttributes() {
        this.attributes = new HashMap<>();
    }


    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Object getAttribute(String key) {
        return attributes != null ? attributes.get(key) : null;
    }

    public void setAttribute(String key, Object value) {
        if (attributes != null) {
            attributes.put(key, value);
        }
    }

    public boolean containsKey(String key) {
        return attributes != null && attributes.containsKey(key);
    }
}
