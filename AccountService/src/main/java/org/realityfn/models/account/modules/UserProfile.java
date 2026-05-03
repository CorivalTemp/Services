package org.realityfn.models.account.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "user_profiles")
public class UserProfile {
    @Id
    private String id;

    @JsonProperty("id")
    @Indexed(unique = true)
    private String accountId; // Reference to Account.id

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("company")
    private String company;

    @JsonProperty("country")
    private String country = "US";

    @JsonProperty("gender")
    private char gender = 'X';

    @JsonProperty("preferredLanguage")
    private String preferredLanguage = "en";

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("canUpdateDisplayName")
    private boolean canUpdateDisplayName;

    @JsonProperty("numberOfDisplayNameChanges")
    private int numberOfDisplayNameChanges = 0;

    @JsonProperty("lastDisplayNameChange")
    private LocalDateTime lastDisplayNameChange;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public UserProfile() {
    }

    public UserProfile(String accountId, String displayName) {
        this.accountId = accountId;
        this.displayName = displayName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCompany() { return company; }
    public void setCompany(String company) {
        this.company = company;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCountry() { return country; }
    public void setCountry(String country) {
        this.country = country;
        this.updatedAt = LocalDateTime.now();
    }

    public char getGender() { return gender; }
    public void setGender(char gender) {
        this.gender = gender;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCanUpdateDisplayName() { return canUpdateDisplayName; }
    public void setCanUpdateDisplayName(boolean canUpdateDisplayName) {
        this.canUpdateDisplayName = canUpdateDisplayName;
        this.updatedAt = LocalDateTime.now();
    }

    public int getNumberOfDisplayNameChanges() { return numberOfDisplayNameChanges; }
    public void setNumberOfDisplayNameChanges(int numberOfDisplayNameChanges) {
        this.numberOfDisplayNameChanges = numberOfDisplayNameChanges;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getLastDisplayNameChange() { return lastDisplayNameChange; }
    public void setLastDisplayNameChange(LocalDateTime lastDisplayNameChange) {
        this.lastDisplayNameChange = lastDisplayNameChange;
        this.updatedAt = LocalDateTime.now();
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

