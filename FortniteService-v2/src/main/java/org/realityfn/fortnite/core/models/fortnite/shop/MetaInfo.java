package org.realityfn.fortnite.core.models.fortnite.shop;

import org.springframework.data.mongodb.core.mapping.Field;

public class MetaInfo {
    @Field("key")
    private String key;

    @Field("value")
    private String value;

    // Default constructor for Spring Data MongoDB
    public MetaInfo() {
    }

    // Constructor for manual object creation
    public MetaInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // Getter and Setter methods
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}