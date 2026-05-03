package org.realityfn.models.account.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.models.account.modules.tfa.SDN;
import org.realityfn.models.account.modules.tfa.TwoFactorAuthentication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "account_security")
public class AccountSecurity {
    @Id
    private String id;

    @JsonProperty("accountId")
    @Indexed(unique = true)
    private String accountId; // Reference to Account.id

    @JsonProperty("tfaEnabled")
    private boolean tfaEnabled;

    @JsonProperty("failedLoginAttempts")
    private int failedLoginAttempts = 0;

    @JsonProperty("lastLogin")
    private LocalDateTime lastLogin;

    @JsonProperty("lastFailedLogin")
    private LocalDateTime lastFailedLogin;

    @JsonProperty("lockoutUntil")
    @Indexed(sparse = true)
    private LocalDateTime lockoutUntil;

    @JsonProperty("cabinedMode")
    private boolean cabinedMode = false;

    @JsonProperty("headless")
    private boolean headless = false;

    @JsonProperty("accountStatus")
    private String accountStatus = "ACTIVE";

    @JsonProperty("accountNotes")
    private String accountNotes;

    @JsonProperty("secured")
    private int secured = 0;

    @JsonProperty("twoFactorAuthentication")
    private TwoFactorAuthentication twoFactorAuthentication = null;

    @JsonProperty("sdn")
    private SDN sdn = null;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public AccountSecurity() {}

    public AccountSecurity(String accountId) {
        this.accountId = accountId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public boolean isTfaEnabled() { return tfaEnabled; }
    public void setTfaEnabled(boolean tfaEnabled) {
        this.tfaEnabled = tfaEnabled;
        this.updatedAt = LocalDateTime.now();
    }

    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getLastFailedLogin() { return lastFailedLogin; }
    public void setLastFailedLogin(LocalDateTime lastFailedLogin) {
        this.lastFailedLogin = lastFailedLogin;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getLockoutUntil() { return lockoutUntil; }
    public void setLockoutUntil(LocalDateTime lockoutUntil) {
        this.lockoutUntil = lockoutUntil;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCabinedMode() { return cabinedMode; }
    public void setCabinedMode(boolean cabinedMode) {
        this.cabinedMode = cabinedMode;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isHeadless() { return headless; }
    public void setHeadless(boolean headless) {
        this.headless = headless;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAccountNotes() { return accountNotes; }
    public void setAccountNotes(String accountNotes) {
        this.accountNotes = accountNotes;
        this.updatedAt = LocalDateTime.now();
    }

    public int getSecured() { return secured; }
    public void setSecured(int secured) {
        this.secured = secured;
        this.updatedAt = LocalDateTime.now();
    }

    public TwoFactorAuthentication getTwoFactorAuthentication() { return twoFactorAuthentication; }
    public void setTwoFactorAuthentication(TwoFactorAuthentication twoFactorAuthentication) {
        this.twoFactorAuthentication = twoFactorAuthentication;
        this.updatedAt = LocalDateTime.now();
    }

    public SDN getSdn() { return sdn; }
    public void setSdn(SDN sdn) {
        this.sdn = sdn;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Utilities
    public void incrementFailedAttempts() {
        this.failedLoginAttempts++;
        this.lastFailedLogin = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void resetFailedAttempts() {
        this.failedLoginAttempts = 0;
        this.lastFailedLogin = null;
        this.lockoutUntil = null;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isLockedOut() {
        return lockoutUntil != null && lockoutUntil.isAfter(LocalDateTime.now());
    }
}
