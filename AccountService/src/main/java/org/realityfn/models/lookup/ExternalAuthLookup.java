package org.realityfn.models.lookup;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.models.account.modules.ExternalAuth;

import java.util.HashMap;

public class ExternalAuthLookup {
    @JsonProperty("id")
    public String Id;

    @JsonProperty("displayName")
    public String DisplayName;

    @JsonProperty("links")
    public HashMap<String, String> Links = new HashMap<>();

    @JsonProperty("externalAuths")
    public HashMap<String, ExternalAuth> ExternalAuths = new HashMap<>();
}
