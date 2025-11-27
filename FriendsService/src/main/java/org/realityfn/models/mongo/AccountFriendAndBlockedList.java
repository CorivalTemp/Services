package org.realityfn.models.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;
import org.realityfn.enums.AcceptingInvitesSettings;
import org.realityfn.enums.MutualPrivaySettings;

import java.util.ArrayList;
import java.util.List;

public class AccountFriendAndBlockedList {

    @BsonId
    @JsonProperty("_id")
    private String accountId;

    @JsonProperty("currentFriends")
    private List<FriendsModel> currentFriends;

    @JsonProperty("blockedUsers")
    private List<BlockedModel> blockedUsers;

    @JsonProperty("pendingRequests")
    private List<FriendsModel> pendingRequests;

    @JsonProperty("sentRequests")
    private List<FriendsModel> sentRequests;

    @JsonProperty("settings")
    private FriendSettings settings;

    public AccountFriendAndBlockedList() {
        this.currentFriends = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();
        this.sentRequests = new ArrayList<>();
        this.settings = new FriendSettings(
                AcceptingInvitesSettings.PUBLIC,
                MutualPrivaySettings.ALL
        );
    }

    public AccountFriendAndBlockedList(String accountId) {
        this();
        this.accountId = accountId;
    }

    public String getAccountId() { return accountId; }

    public List<FriendsModel> getCurrentFriends() { return currentFriends; }
    public void setCurrentFriends(List<FriendsModel> currentFriends) {
        this.currentFriends = currentFriends;
    }

    public List<BlockedModel> getBlockedUsers() { return blockedUsers; }
    public void setBlockedUsers(List<BlockedModel> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public List<FriendsModel> getPendingRequests() { return pendingRequests; }
    public void setPendingRequests(List<FriendsModel> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public List<FriendsModel> getSentRequests() { return sentRequests; }
    public void setSentRequests(List<FriendsModel> sentRequests) { this.sentRequests = sentRequests; }

    public FriendSettings getSettings() { return settings; }
    public void setSettings(FriendSettings settings) {
        this.settings = settings;
    }

    public void sendRequestToUser(String accountId, String created) {
        sentRequests.add(new FriendsModel(accountId, created));
    }

    public void receiveRequestToUser(String accountId, String created) {
        pendingRequests.add(new FriendsModel(accountId, created));
    }

    public void handlingIncomingRequest(String accountId, boolean isAccepting) {
        for (FriendsModel request : pendingRequests) {
            if (request.getAccountId().equals(accountId)) {
                pendingRequests.remove(request);
                if (isAccepting) currentFriends.add(request);
                break;
            }
        }
    }

    public void handleOutgoingRequest(String accountId, boolean isAccepting) {
        for (FriendsModel request : sentRequests) {
            if (request.getAccountId().equals(accountId)) {
                sentRequests.remove(request);
                if (isAccepting) currentFriends.add(request);
                break;
            }
        }
    }

    public void removeFriend(String accountId) {
        for (FriendsModel friend : currentFriends) {
            if (friend.getAccountId().equals(accountId)) {
                currentFriends.remove(friend);
                break;
            }
        }
    }

    public void blockUser(String accountId, String created) {
        blockedUsers.add(new BlockedModel(accountId, created));
    }

    public void unblockUser(String accountId) {
        for (BlockedModel user : blockedUsers) {
            if (user.getAccountId().equals(accountId)) {
                blockedUsers.remove(user);
                break;
            }
        }
    }

    public boolean isFriend(String accountId) {
        return currentFriends.stream().anyMatch(f -> f.getAccountId().equals(accountId));
    }

    public boolean hasIncomingRequestFrom(String accountId) {
        return pendingRequests.stream().anyMatch(f -> f.getAccountId().equals(accountId));
    }

    public boolean hasOutgoingRequestTo(String accountId) {
        return sentRequests.stream().anyMatch(f -> f.getAccountId().equals(accountId));
    }

    public boolean isBlocked(String accountId) {
        return blockedUsers.stream().anyMatch(f -> f.getAccountId().equals(accountId));
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void addFriendByConstruct(FriendsModel friendsModel) {
        currentFriends.add(friendsModel);
    }
}
