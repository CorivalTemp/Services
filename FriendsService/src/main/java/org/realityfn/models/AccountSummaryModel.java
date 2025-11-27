package org.realityfn.models;

import org.realityfn.models.mongo.BlockedModel;
import org.realityfn.models.mongo.FriendSettings;
import org.realityfn.models.mongo.FriendsModel;

import java.util.HashMap;
import java.util.List;

public class AccountSummaryModel {
    private List<FriendsModel> friends;
    private List<FriendsModel> incoming;
    private List<FriendsModel> outgoing;
    private List<String> suggested;
    private List<BlockedModel> blocklist;
    private FriendSettings settings;
    private HashMap<String, Boolean> limitsReached;

    public AccountSummaryModel() {}

    public AccountSummaryModel(List<FriendsModel> friends, List<FriendsModel> incoming, List<FriendsModel> outgoing, List<BlockedModel> blocklist, FriendSettings settings) {
        this.friends = friends;
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.suggested = List.of();
        this.blocklist = blocklist;
        this.settings = settings;
        HashMap<String, Boolean> payload = new HashMap<String, Boolean>();
        payload.put("incoming", false);
        payload.put("outgoing", false);
        payload.put("accepted", false);
        this.limitsReached = payload;
    }

    public List<FriendsModel> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsModel> friends) {
        this.friends = friends;
    }

    public List<FriendsModel> getIncoming() {
        return incoming;
    }

    public void setIncoming(List<FriendsModel> incoming) {
        this.incoming = incoming;
    }

    public List<FriendsModel> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(List<FriendsModel> outgoing) {
        this.outgoing = outgoing;
    }

    public List<String> getSuggested() {
        return suggested;
    }

    public void setSuggested(List<String> suggested) {
        this.suggested = suggested;
    }

    public List<BlockedModel> getBlocklist() {
        return blocklist;
    }

    public void setBlocklist(List<BlockedModel> blocklist) {
        this.blocklist = blocklist;
    }

    public FriendSettings getSettings() {
        return settings;
    }

    public void setSettings(FriendSettings settings) {
        this.settings = settings;
    }

    public HashMap<String, Boolean> getLimitsReached() {
        HashMap<String, Boolean> payload = new HashMap<String, Boolean>();
        payload.put("incoming", false);
        payload.put("outgoing", false);
        payload.put("accepted", false);
        return payload;
    }

    public void setLimitsReached() {
        HashMap<String, Boolean> payload = new HashMap<String, Boolean>();
        payload.put("incoming", false);
        payload.put("outgoing", false);
        payload.put("accepted", false);
        this.limitsReached = payload;
    }
}
