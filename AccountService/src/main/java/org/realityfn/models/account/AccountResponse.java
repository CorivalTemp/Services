package org.realityfn.models.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("failedLoginAttempts")
    private int failedLoginAttempts;

    @JsonProperty("numberOfDisplayNameChanges")
    private int numberOfDisplayNameChanges;

    @JsonProperty("ageGroup")
    private String ageGroup;

    @JsonProperty("headless")
    private boolean headless;

    @JsonProperty("country")
    private String country;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("company")
    private String company;

    @JsonProperty("preferredLanguage")
    private String preferredLanguage;

    @JsonProperty("canUpdateDisplayName")
    private boolean canUpdateDisplayName;

    @JsonProperty("tfaEnabled")
    private boolean tfaEnabled;

    @JsonProperty("emailVerified")
    private boolean emailVerified;

    @JsonProperty("minorVerified")
    private boolean minorVerified;

    @JsonProperty("minorExpected")
    private boolean minorExpected;

    @JsonProperty("minorStatus")
    private String minorStatus;

    @JsonProperty("cabinedMode")
    private boolean cabinedMode;

    @JsonProperty("hasHashedEmail")
    private boolean hasHashedEmail;

    // Constructors
    public AccountResponse() {}

    public AccountResponse(CompleteAccount account) {
        this.id = account.getAccountId();
        this.displayName = account.getDisplayName();
        this.name = account.getProfile().getName();
        this.email = account.getAccount().getUser();
        this.failedLoginAttempts = account.getSecurity().getFailedLoginAttempts();
        this.numberOfDisplayNameChanges = account.getProfile().getNumberOfDisplayNameChanges();
        this.ageGroup = account.getVerification().getAgeGroup();
        this.headless = account.getSecurity().isHeadless();
        this.country = account.getProfile().getCountry();
        this.lastName = account.getProfile().getLastName();
        this.phoneNumber = account.getProfile().getPhoneNumber();
        this.company = account.getProfile().getCompany();
        this.preferredLanguage = account.getProfile().getPreferredLanguage();
        this.canUpdateDisplayName = account.getProfile().isCanUpdateDisplayName();
        this.tfaEnabled = account.getSecurity().isTfaEnabled();
        this.emailVerified = account.getVerification().isEmailVerified();
        this.minorVerified = account.getVerification().isMinorVerified();
        this.minorExpected = account.getVerification().isMinorExpected();
        this.minorStatus = account.getVerification().getMinorStatus();
        this.cabinedMode = account.getSecurity().isCabinedMode();
        this.hasHashedEmail = account.getVerification().isHasHashedEmail();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }

    public int getNumberOfDisplayNameChanges() { return numberOfDisplayNameChanges; }
    public void setNumberOfDisplayNameChanges(int numberOfDisplayNameChanges) { this.numberOfDisplayNameChanges = numberOfDisplayNameChanges; }

    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }

    public boolean isHeadless() { return headless; }
    public void setHeadless(boolean headless) { this.headless = headless; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public boolean isCanUpdateDisplayName() { return canUpdateDisplayName; }
    public void setCanUpdateDisplayName(boolean canUpdateDisplayName) { this.canUpdateDisplayName = canUpdateDisplayName; }

    public boolean isTfaEnabled() { return tfaEnabled; }
    public void setTfaEnabled(boolean tfaEnabled) { this.tfaEnabled = tfaEnabled; }

    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }

    public boolean isMinorVerified() { return minorVerified; }
    public void setMinorVerified(boolean minorVerified) { this.minorVerified = minorVerified; }

    public boolean isMinorExpected() { return minorExpected; }
    public void setMinorExpected(boolean minorExpected) { this.minorExpected = minorExpected; }

    public String getMinorStatus() { return minorStatus; }
    public void setMinorStatus(String minorStatus) { this.minorStatus = minorStatus; }

    public boolean isCabinedMode() { return cabinedMode; }
    public void setCabinedMode(boolean cabinedMode) { this.cabinedMode = cabinedMode; }

    public boolean isHasHashedEmail() { return hasHashedEmail; }
    public void setHasHashedEmail(boolean hasHashedEmail) { this.hasHashedEmail = hasHashedEmail; }

}
