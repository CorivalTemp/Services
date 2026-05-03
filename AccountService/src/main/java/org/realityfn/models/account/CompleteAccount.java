package org.realityfn.models.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.models.account.modules.*;

import java.util.ArrayList;
import java.util.List;

// Composite DTO for full account data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompleteAccount {
    @JsonProperty("account")
    private Account account;

    @JsonProperty("profile")
    private UserProfile profile;

    @JsonProperty("externalAuths")
    private List<ExternalAuth> externalAuths;

    @JsonProperty("verification")
    private AccountVerification verification;

    @JsonProperty("security")
    private AccountSecurity security;

    // Constructors
    public CompleteAccount() {}

    public CompleteAccount(Account account, UserProfile profile,
                           List<ExternalAuth> externalAuths,
                           AccountVerification verification,
                           AccountSecurity security) {
        this.account = account;
        this.profile = profile;
        this.externalAuths = externalAuths;
        this.verification = verification;
        this.security = security;
    }

    // Getters and Setters
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public UserProfile getProfile() { return profile; }
    public void setProfile(UserProfile profile) { this.profile = profile; }

    public List<ExternalAuth> getExternalAuths() { return externalAuths != null ? externalAuths : new ArrayList<ExternalAuth>(); }
    public void setExternalAuths(List<ExternalAuth> externalAuths) { this.externalAuths = externalAuths; }

    public AccountVerification getVerification() { return verification; }
    public void setVerification(AccountVerification verification) { this.verification = verification; }

    public AccountSecurity getSecurity() { return security; }
    public void setSecurity(AccountSecurity security) { this.security = security; }

    // Convenience Methods
    public String getAccountId() {
        return account != null ? account.getId() : null;
    }

    public String getDisplayName() {
        return profile != null ? profile.getDisplayName() : null;
    }

    public boolean isEmailVerified() {
        return verification != null && verification.isEmailVerified();
    }

    public boolean isTfaEnabled() {
        return security != null && security.isTfaEnabled();
    }
}
