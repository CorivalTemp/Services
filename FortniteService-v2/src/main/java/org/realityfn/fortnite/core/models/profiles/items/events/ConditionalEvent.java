package org.realityfn.fortnite.core.models.profiles.items.events;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class ConditionalEvent {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String instanceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String eventName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String eventStart;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String eventEnd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Action startActions;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Action endActions;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, String> metaData;
}
