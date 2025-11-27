package org.realityfn.fortnite.core.models.lightswitch;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LightswitchServiceStatus {

    @JsonProperty("serviceInstanceId")
    private String serviceInstanceId;

    @JsonProperty("banned")
    private boolean banned;

    @JsonProperty("allowedActions")
    private List<String> allowedActions;

    public LightswitchServiceStatus() {
    }

    public LightswitchServiceStatus(String serviceInstanceId, boolean banned, List<String> allowedActions) {
        this.serviceInstanceId = serviceInstanceId;
        this.banned = banned;
        this.allowedActions = allowedActions;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public List<String> getAllowedActions() {
        return allowedActions;
    }

    public void setAllowedActions(List<String> allowedActions) {
        this.allowedActions = allowedActions;
    }
}