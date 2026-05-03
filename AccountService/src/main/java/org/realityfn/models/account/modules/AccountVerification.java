package org.realityfn.models.account.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "account_verification")
public class AccountVerification {
    @Id
    private String id;

    @JsonProperty("accountId")
    @Indexed(unique = true)
    private String accountId; // Reference to Account.id

    @JsonProperty("emailVerified")
    private boolean emailVerified = false;

    @JsonProperty("hasHashedEmail")
    private boolean hasHashedEmail = false;

    @JsonProperty("minorStatus")
    @Indexed
    private String minorStatus = "NOT_MINOR";

    @JsonProperty("minorExpected")
    private boolean minorExpected = false;

    @JsonProperty("minorVerified")
    private boolean minorVerified = false;

    @JsonProperty("ageGroup")
    private String ageGroup = "UNKNOWN";

    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @JsonProperty("verifiedAt")
    public LocalDateTime verifiedAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    // Constructors
    public AccountVerification() {}

    public AccountVerification(String accountId) {
        this.accountId = accountId;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        if (emailVerified && this.verifiedAt == null) {
            this.verifiedAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isHasHashedEmail() { return hasHashedEmail; }
    public void setHasHashedEmail(boolean hasHashedEmail) {
        this.hasHashedEmail = hasHashedEmail;
        this.updatedAt = LocalDateTime.now();
    }

    public String getMinorStatus() { return minorStatus; }
    public void setMinorStatus(String minorStatus) {
        this.minorStatus = minorStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isMinorExpected() { return minorExpected; }
    public void setMinorExpected(boolean minorExpected) {
        this.minorExpected = minorExpected;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isMinorVerified() { return minorVerified; }
    public void setMinorVerified(boolean minorVerified) {
        this.minorVerified = minorVerified;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getVerifiedAt() { return verifiedAt; }
    public void setVerifiedAt(LocalDateTime verifiedAt) { this.verifiedAt = verifiedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
