package org.realityfn.models.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataBody {
    @JsonProperty("key")
    public String Key;

    @JsonProperty("value")
    public String Value;
}
