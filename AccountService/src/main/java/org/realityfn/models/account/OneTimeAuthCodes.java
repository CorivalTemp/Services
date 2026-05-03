package org.realityfn.models.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.realityfn.models.authorization.PermissionMapping;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "one_time_auth_codes")
public class OneTimeAuthCodes {

    private String code;
    private String creatingClientId;
    private String consumingClientId;
    private LocalDateTime expiresIn;
    private String creatingAccessToken;
    private String owningAccountId;
    private String authCodeType;

    public OneTimeAuthCodes() {}

    public OneTimeAuthCodes(String code, String creatingClientId, String consumingClientId,
                             LocalDateTime expiresIn, String creatingAccessToken, String owningAccountId, String authCodeType) {
        this.code = code;
        this.creatingClientId = creatingClientId;
        this.consumingClientId = consumingClientId;
        this.expiresIn = expiresIn;
        this.creatingAccessToken = creatingAccessToken;
        this.owningAccountId = owningAccountId;
        this.authCodeType = authCodeType;
    }

    // Getters and them setters ifykyk
    public String getCode() {
        return code;
    }

    public String getCreatingClientId() {
        return creatingClientId;
    }

    public String getConsumingClientId() {
        return consumingClientId;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public String getCreatingAccessToken() {
        return creatingAccessToken;
    }

    public String getOwningAccountId() {
        return owningAccountId;
    }

    public String getAuthCodeType() {
        return authCodeType;
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

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setCreatingAccessToken(String creatingAccessToken) {
        this.creatingAccessToken = creatingAccessToken;
    }

    public void setOwningAccountId(String owningAccountId) {
        this.owningAccountId = owningAccountId;
    }

    public void setAuthCodeType(String authCodeType) {
        this.authCodeType = authCodeType;
    }

    public boolean isExpired() {
        return expiresIn != null && LocalDateTime.now().isAfter(expiresIn);
    }

    public boolean isConsumingClient(String clientId) {
        // ignore the check lol
        if (this.consumingClientId == null) return true;
        System.out.println("Exchange code asked for " + this.consumingClientId + " While redeeming is: " + clientId);
        System.out.println(this.consumingClientId.equals(clientId));
        return this.consumingClientId.equals(clientId);
    }
}