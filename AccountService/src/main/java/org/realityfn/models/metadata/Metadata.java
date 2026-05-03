package org.realityfn.models.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "Metadata")
public class Metadata {
    @JsonProperty("id")
    public String accountId;

    @JsonProperty("metaData")
    public Map<String, String> metaData;
}
