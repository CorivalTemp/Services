package org.realityfn.models.external_auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class XboxAccount {
    @JsonProperty("display_name")
    public String DisplayName;

    @JsonProperty("id")
    public String Id;

    public XboxAccount() {}
    public XboxAccount(String displayName, String id) {
        DisplayName = displayName;
        Id = id;
    }
}
