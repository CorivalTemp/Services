package org.realityfn.fortnite.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;

public class Keychain {

    @BsonId
    private String id;

    @JsonProperty("key")
    private String key;
    public Keychain() {}

    public Keychain(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}