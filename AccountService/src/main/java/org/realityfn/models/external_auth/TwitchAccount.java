package org.realityfn.models.external_auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwitchAccount {
    @JsonProperty("display_name")
    public String DisplayName;

    @JsonProperty("id")
    public String Id;

    public TwitchAccount() {}
    public TwitchAccount(String displayName, String id) {
        DisplayName = displayName;
        Id = id;
    }
}
