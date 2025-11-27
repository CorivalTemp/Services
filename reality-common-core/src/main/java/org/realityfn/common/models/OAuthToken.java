package org.realityfn.common.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OAuthToken {
    public String access_token;

    public String token;

    public String session_id;

    public String token_type;

    public String client_id;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean internal_client;

    public String client_service;

    public String account_id;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int expires_in;

    public String expires_at;

    public String refresh_token;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int refresh_expires;

    public String refresh_expires_at;

    public String auth_method;

    public String displayName;

    public String app;

    public String in_app_id;

    public String product_id;

    public String application_id;

    public List<Permission> perms;

    public boolean isClientCredentials() {
        return auth_method.equals("client_credentials");
    }
}