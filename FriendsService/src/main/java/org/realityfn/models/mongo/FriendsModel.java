package org.realityfn.models.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class FriendsModel {

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("groups")
    private String[] groups;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("note")
    private String note;

    @JsonProperty("favorite")
    private boolean favorite;

    @JsonProperty("created")
    private String created;

    public FriendsModel() {}

    public FriendsModel(String accountId, String created) {
        this.accountId = accountId;
        this.groups = new String[0]; // Default to no groups
        this.alias = null; // Default to no alias
        this.note = null; // Default to no note
        this.favorite = false; // Default to not favorite
        this.created = created;
    }

    // Getters and setters

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String[] getGroups() { return groups; }
    public void setGroups(String[] groups) { this.groups = groups; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }
}