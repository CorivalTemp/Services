package org.realityfn.models.account.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalAuthBody {
    @JsonProperty("authType")
    public String AuthType;

    @JsonProperty("externalAuthToken")
    public String ExternalAuthToken;
}
