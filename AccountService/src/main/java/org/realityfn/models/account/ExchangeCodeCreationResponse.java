package org.realityfn.models.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeCodeCreationResponse {

    private int expiresInSeconds;
    private String code;
    private String creatingClientId;
    private String consumingClientId;

    public ExchangeCodeCreationResponse() {}

    public ExchangeCodeCreationResponse(int expiresInSeconds, String code, String creatingClientId, String consumingClientId) {
        this.expiresInSeconds = expiresInSeconds;
        this.code = code;
        this.creatingClientId = creatingClientId;
        this.consumingClientId = consumingClientId;
    }

    // Getters and them setters ifykyk

    public int getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public String getCode() {
        return code;
    }

    public String getCreatingClientId() {
        return creatingClientId;
    }

    public String getConsumingClientId() {
        return consumingClientId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreatingClientId(String creatingClientId) {
        this.creatingClientId = creatingClientId;
    }

    public void setConsumingClientId(String consumingClientId) {
        this.consumingClientId = consumingClientId;
    }
}