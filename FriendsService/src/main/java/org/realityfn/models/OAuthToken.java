package org.realityfn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OAuthToken {
    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonProperty("expires_at")
    public Instant expiresAt;

    @JsonProperty("refresh_expires_at")
    public Instant refreshExpiresAt;

    @JsonProperty("token_type")
    public String tokenType;

    @JsonProperty("client_id")
    public String clientId;

    @JsonProperty("internal_client")
    public boolean internalClient;

    @JsonProperty("client_service")
    public String clientService;

    @JsonProperty("account_id")
    public String accountId;

    @JsonProperty("display_name")
    public String displayName;

    @JsonProperty("app")
    public String app;

    @JsonProperty("in_app_id")
    public String inAppId;

    @JsonProperty("product_id")
    public String productId;

    @JsonProperty("application_id")
    public String applicationId;

    @JsonProperty("session_id")
    public String sessionId;

    @JsonProperty("auth_method")
    public String authMethod;

    @JsonProperty("perms")
    public List<PermissionMapping> permissions = new ArrayList<>();
}