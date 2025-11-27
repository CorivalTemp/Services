package org.realityfn.fortnite.core.models.gamesessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionEncryptionKeyResponse {
    @JsonProperty("accountId")
    public String accountId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("key")
    public String key;

    public SessionEncryptionKeyResponse() {}
    public SessionEncryptionKeyResponse(String AccountId, String SessionId, String Key) {
        this.accountId = AccountId;
        this.sessionId = SessionId;
        this.key = Key;
    }
}
