package org.realityfn.common.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.common.enums.Actions;

public class Permission {
    private final String resource;
    private final int action;

    public String getResource() { return resource; }
    public int getAction() { return action; }

    @JsonCreator
    public Permission(
            @JsonProperty("resource") String resource,
            @JsonProperty("action") int action
    ) {
        this.resource = resource;
        this.action = action;
    }

    public String getActionNames() {
        return Actions.grabActionName(this.action);
    }
}