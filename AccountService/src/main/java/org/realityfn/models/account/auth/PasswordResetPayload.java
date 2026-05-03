package org.realityfn.models.account.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordResetPayload {
    @JsonProperty("password")
    public String password;
}
