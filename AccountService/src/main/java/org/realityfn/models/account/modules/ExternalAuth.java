package org.realityfn.models.account.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "external_auths")
@CompoundIndex(def = "{'externalAuthId': 1, 'type': 1}", unique = true)
public class ExternalAuth {
    @Id
    private String id;

    @JsonProperty("accountId")
    @Indexed
    private String accountId; // Reference to Account.id

    @JsonProperty("type")
    @Indexed
    private String type;

    @JsonProperty("externalAuthId")
    private String externalAuthId;

    @JsonProperty("externalAuthIdType")
    private String externalAuthIdType;

    @JsonProperty("externalDisplayName")
    private String externalDisplayName;

    @JsonProperty("authIds")
    private List<AuthId> authIds;

    @JsonProperty("dateAdded")
    private LocalDateTime dateAdded = LocalDateTime.now();

    @JsonProperty("isActive")
    private boolean isActive = true;

    // Constructors
    public ExternalAuth() {}

    public ExternalAuth(String accountId, String type) {
        this.accountId = accountId;
        this.type = type;
        this.externalAuthId = null; // This is only used with a promise that this will be set
        this.dateAdded = LocalDateTime.now();
        this.isActive = true;
    }

    public ExternalAuth(String accountId, String type, String externalAuthId) {
        this.accountId = accountId;
        this.type = type;
        this.externalAuthId = externalAuthId;
        this.dateAdded = LocalDateTime.now();
        this.isActive = true;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getExternalAuthId() { return externalAuthId; }
    public void setExternalAuthId(String externalAuthId) { this.externalAuthId = externalAuthId; }

    public String getExternalAuthIdType() { return externalAuthIdType; }
    public void setExternalAuthIdType(String externalAuthIdType) { this.externalAuthIdType = externalAuthIdType; }

    public String getExternalDisplayName() { return externalDisplayName; }
    public void setExternalDisplayName(String externalDisplayName) { this.externalDisplayName = externalDisplayName; }

    public List<AuthId> getAuthIds() { return authIds; }
    public void setAuthIds(List<AuthId> authIds) { this.authIds = authIds; }

    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    // Inner class for AuthId
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthId {
        @JsonProperty("Id")
        private String id;

        @JsonProperty("Type")
        private String type;

        public AuthId() {}

        public AuthId(String id, String type) {
            this.id = id;
            this.type = type;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
}
