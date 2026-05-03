package org.realityfn.models.account.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "user_profiles")
public class OwnedAccountResponse {

    private String id;
    private String displayName;
    private String name;
    private String email;
    private int failedLoginAttempts;
    private LocalDateTime lastLogin;
    private int numberOfDisplayNameChanges;
    private String ageGroup;
    private boolean headless;
    private String country;
    private String lastName;
    private String phoneNumber;
    private String company;
    private String preferredLanguage;
    private LocalDateTime lastDisplayNameChange;
    private boolean canUpdateDisplayName;
    private boolean tfaEnabled;
    private boolean emailVerified;
    private boolean minorVerified;
    private boolean minorExpected;
    private String minorStatus;
    private LocalDateTime guardianChallengeTimestamp;
    private boolean siweNotificationEnabled;
    private boolean cabinedMode;
    private boolean hasHashedEmail;
    private LocalDateTime lastReviewedSecuritySettings;

    // Default constructor
    public OwnedAccountResponse() {}

    // Getters
    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public int getNumberOfDisplayNameChanges() {
        return numberOfDisplayNameChanges;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public boolean isHeadless() {
        return headless;
    }

    public String getCountry() {
        return country;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public LocalDateTime getLastDisplayNameChange() {
        return lastDisplayNameChange;
    }

    public boolean isCanUpdateDisplayName() {
        return canUpdateDisplayName;
    }

    public boolean isTfaEnabled() {
        return tfaEnabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public boolean isMinorVerified() {
        return minorVerified;
    }

    public boolean isMinorExpected() {
        return minorExpected;
    }

    public String getMinorStatus() {
        return minorStatus;
    }

    public LocalDateTime getGuardianChallengeTimestamp() {
        return guardianChallengeTimestamp;
    }

    public boolean isSiweNotificationEnabled() {
        return siweNotificationEnabled;
    }

    public boolean isCabinedMode() {
        return cabinedMode;
    }

    public boolean isHashedEmail() {
        return hasHashedEmail;
    }

    public LocalDateTime getLastReviewedSecuritySettings() {
        return lastReviewedSecuritySettings;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setNumberOfDisplayNameChanges(int numberOfDisplayNameChanges) {
        this.numberOfDisplayNameChanges = numberOfDisplayNameChanges;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public void setLastDisplayNameChange(LocalDateTime lastDisplayNameChange) {
        this.lastDisplayNameChange = lastDisplayNameChange;
    }

    public void setCanUpdateDisplayName(boolean canUpdateDisplayName) {
        this.canUpdateDisplayName = canUpdateDisplayName;
    }

    public void setTfaEnabled(boolean tfaEnabled) {
        this.tfaEnabled = tfaEnabled;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setMinorVerified(boolean minorVerified) {
        this.minorVerified = minorVerified;
    }

    public void setMinorExpected(boolean minorExpected) {
        this.minorExpected = minorExpected;
    }

    public void setMinorStatus(String minorStatus) {
        this.minorStatus = minorStatus;
    }

    public void setGuardianChallengeTimestamp(LocalDateTime guardianChallengeTimestamp) {
        this.guardianChallengeTimestamp = guardianChallengeTimestamp;
    }

    public void setSiweNotificationEnabled(boolean siweNotificationEnabled) {
        this.siweNotificationEnabled = siweNotificationEnabled;
    }

    public void setCabinedMode(boolean cabinedMode) {
        this.cabinedMode = cabinedMode;
    }

    public void setHashedEmail(boolean hasHashedEmail) {
        this.hasHashedEmail = hasHashedEmail;
    }

    public void setLastReviewedSecuritySettings(LocalDateTime lastReviewedSecuritySettings) {
        this.lastReviewedSecuritySettings = lastReviewedSecuritySettings;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", failedLoginAttempts=" + failedLoginAttempts +
                ", lastLogin=" + lastLogin +
                ", numberOfDisplayNameChanges=" + numberOfDisplayNameChanges +
                ", ageGroup='" + ageGroup + '\'' +
                ", headless=" + headless +
                ", country='" + country + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", company='" + company + '\'' +
                ", preferredLanguage='" + preferredLanguage + '\'' +
                ", lastDisplayNameChange=" + lastDisplayNameChange +
                ", canUpdateDisplayName=" + canUpdateDisplayName +
                ", tfaEnabled=" + tfaEnabled +
                ", emailVerified=" + emailVerified +
                ", minorVerified=" + minorVerified +
                ", minorExpected=" + minorExpected +
                ", minorStatus='" + minorStatus + '\'' +
                ", guardianChallengeTimestamp=" + guardianChallengeTimestamp +
                ", siweNotificationEnabled=" + siweNotificationEnabled +
                ", cabinedMode=" + cabinedMode +
                ", hasHashedEmail=" + hasHashedEmail +
                ", lastReviewedSecuritySettings=" + lastReviewedSecuritySettings +
                '}';
    }
}