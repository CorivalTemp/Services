package org.realityfn.models.mongo;

import org.bson.codecs.pojo.annotations.BsonId;

public class FriendSettings {

    private String acceptInvites;
    private String mutualPrivacy;

    public FriendSettings() {}

    public FriendSettings(String acceptInvites, String mutualPrivacy) {
        this.acceptInvites = acceptInvites;
        this.mutualPrivacy = mutualPrivacy;
    }

    public String getAcceptInvites() { return acceptInvites; }
    public void setAcceptInvites(String acceptInvites) { this.acceptInvites = acceptInvites; }

    public String getMutualPrivacy() { return mutualPrivacy; }
    public void setMutualPrivacy(String mutualPrivacy) { this.mutualPrivacy = mutualPrivacy; }
}