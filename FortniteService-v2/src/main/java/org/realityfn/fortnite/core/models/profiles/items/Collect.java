package org.realityfn.fortnite.core.models.profiles.items;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Collect {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String seenState;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String variantTag;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> contextTags;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public CollectedProperties properties;
}
