package org.realityfn.models.authorization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {
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

    public List<PermissionMapping> perms;
}
