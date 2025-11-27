package org.realityfn.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.Optional;

import org.realityfn.annotations.OAuth;
import org.realityfn.enums.Actions;
import org.realityfn.errorhandling.exceptions.common.ServerErrorException;
import org.realityfn.errorhandling.exceptions.friends.*;
import org.realityfn.models.AccountSummaryModel;
import org.realityfn.models.mongo.AccountFriendAndBlockedList;
import org.realityfn.models.mongo.FriendsModel;
import org.realityfn.utils.XMPPEventService;
import org.realityfn.utils.database.FriendsDAO;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/api")
public class FriendsController {

    private final FriendsDAO friendsDAO;

    @Inject
    private XMPPEventService xmppService;

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public FriendsController() {
        this.friendsDAO = new FriendsDAO();
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/v1/{accountId}/friends")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.READ)
    public Response getAccountFriends(@PathParam("accountId") String accountId) {
        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);
        List<FriendsModel> currentFriendsList = userFriendsInfo.getCurrentFriends();
        if (currentFriendsList == null) {
            throw new ServerErrorException();
        }
        return Response.ok(currentFriendsList).build();
    }

    /**
     * Fetch requests sent from others to the account duh...
     * @param accountId take a wild guess...
     */
    @GET
    @Path("/v1/{accountId}/incoming")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.READ)
    public Response getIncomingRequests(@PathParam("accountId") String accountId) {
        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);
        List<FriendsModel> incomingRequestsList = userFriendsInfo.getPendingRequests();
        if (incomingRequestsList == null) {
            throw new ServerErrorException();
        }
        return Response.ok(incomingRequestsList).build();
    }
    /**
     * Exact opposite as the one above (shows requests sent by the account)
     * @param accountId take a wild guess...
     */
    @GET
    @Path("/v1/{accountId}/outgoing")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.READ)
    public Response getOutgoingRequests(@PathParam("accountId") String accountId) {
        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);
        List<FriendsModel> sentRequestsList = userFriendsInfo.getSentRequests();
        if (sentRequestsList == null) {
            throw new ServerErrorException();
        }
        return Response.ok(sentRequestsList).build();
    }

    @DELETE
    @Path("/v1/{accountId}/friends")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.DELETE)
    public Response deleteAllAccountFriend(@PathParam("accountId") String accountId) {
        friendsDAO.deleteAllFriends(accountId);
        return Response.noContent().build();
    }

    @GET
    @Path("/v1/{accountId}/friends/{friendAccountId}")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.READ)
    public Response getAccountFriend(@PathParam("accountId") String accountId, @PathParam("friendAccountId") String friendAccountId) throws DuplicateFriendshipException {
        if (accountId.equals(friendAccountId)) {
            throw new InvalidFriendshipWithSelfException();
        }

        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);

        // Ensure the friend's account exists before proceeding JIRA: RLTY-20
        Optional<AccountFriendAndBlockedList> friendedUserFriendsInfoOpt = friendsDAO.getFriendInfoWithAccountId(friendAccountId);
        if (friendedUserFriendsInfoOpt.isEmpty()) {
            throw new AccountDoesntExistException(friendAccountId);
        }

        FriendsModel currentFriend = null;

        List<FriendsModel> currentFriendsList = userFriendsInfo.getCurrentFriends();
        for (FriendsModel friend : currentFriendsList) {
            if (friend.getAccountId().equals(friendAccountId)) {
                currentFriend = friend;
                break;
            }
        }

        if (currentFriend != null) {
            return Response.ok(currentFriend).build();
        }
        throw new FriendshipDoesntExistException(accountId, friendAccountId);
    }

    @PUT
    @Path("/v1/{accountId}/friends/{friendAccountId}/note")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.UPDATE)
    public Response editFriendNote(@PathParam("accountId") String accountId, @PathParam("friendAccountId") String friendAccountId) throws DuplicateFriendshipException {
        if (accountId.equals(friendAccountId)) {
            throw new InvalidFriendshipWithSelfException();
        }

        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);

        // Ensure the friend's account exists before proceeding JIRA: RLTY-20
        Optional<AccountFriendAndBlockedList> friendedUserFriendsInfoOpt = friendsDAO.getFriendInfoWithAccountId(friendAccountId);
        if (friendedUserFriendsInfoOpt.isEmpty()) {
            throw new AccountDoesntExistException(friendAccountId);
        }

        FriendsModel currentFriend = null;

        List<FriendsModel> currentFriendsList = userFriendsInfo.getCurrentFriends();
        for (FriendsModel friend : currentFriendsList) {
            if (friend.getAccountId().equals(friendAccountId)) {
                currentFriend = friend;
                break;
            }
        }

        if (currentFriend != null) {
            return Response.ok(currentFriend).build();
        }
        throw new FriendshipDoesntExistException(accountId, friendAccountId);
    }

    @POST
    @Path("/v1/{accountId}/friends/{friendAccountId}")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.CREATE)
    public Response addAccountFriend(@PathParam("accountId") String accountId, @PathParam("friendAccountId") String friendAccountId) throws DuplicateFriendshipException {
        if (accountId.equals(friendAccountId)) {
            throw new InvalidFriendshipWithSelfException();
        }

        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);

        // Ensure the friend's account exists before proceeding JIRA: RLTY-20
        Optional<AccountFriendAndBlockedList> friendedUserFriendsInfoOpt = friendsDAO.getFriendInfoWithAccountId(friendAccountId);
        if (friendedUserFriendsInfoOpt.isEmpty()) {
            throw new AccountDoesntExistException(friendAccountId);
        }

        // Check if we already are friends with the user or sent a request beforehand.
        if (userFriendsInfo.isFriend(friendAccountId)) {
            throw new DuplicateFriendshipException(accountId, friendAccountId);
        }

        if (userFriendsInfo.hasOutgoingRequestTo(friendAccountId)) {
            throw new RequestAlreadySentToAccountException(friendAccountId);
        }

        // Check if the user is blocked by the friendAccountId
        if (friendedUserFriendsInfoOpt.get().isBlocked(accountId) || userFriendsInfo.isBlocked(friendAccountId)) {
            throw new CannotFriendBlockedAccountException(accountId, friendAccountId);
        }

        // Check if there's an incoming request from the user and accept it.
        if (userFriendsInfo.hasIncomingRequestFrom(friendAccountId)) {
            friendsDAO.handleRequestFromUser(accountId, friendAccountId, true);
            Map<String, Object> payload = new java.util.HashMap<>();
            payload.put("accountId", accountId);
            payload.put("status", "ACCEPTED");
            payload.put("direction", "INCOMING");
            payload.put("created", DATE_TIME_FORMATTER.format(java.time.Instant.now()));
            payload.put("favorite", false);

            String friendRecipient = friendAccountId + "@prod.realityfn.org";

            xmppService.sendEvent(
                    friendRecipient,
                    "com.epicgames.friends.core.apiobjects.Friend",
                    payload
            );

            String recipient = accountId + "@prod.realityfn.org";
            payload.put("status", "ACCEPTED");
            payload.put("direction", "OUTGOING");

            xmppService.sendEvent(
                    recipient,
                    "com.epicgames.friends.core.apiobjects.Friend",
                    payload
            );
            return Response.noContent().build();
        }

        // If no incoming request was found, send a new one.
        friendsDAO.sendRequestToUser(accountId, friendAccountId);

        // XMPP Notification
        String recipient = friendAccountId + "@prod.realityfn.org";

        Map<String, Object> newPayload = new java.util.HashMap<>();
        newPayload.put("accountId", accountId);
        newPayload.put("status", "PENDING");
        newPayload.put("direction", "INBOUND");
        newPayload.put("created", DATE_TIME_FORMATTER.format(java.time.Instant.now()));
        newPayload.put("favorite", false);

        xmppService.sendEvent(
                recipient,
                "com.epicgames.friends.core.apiobjects.Friend",
                newPayload
        );
        /*
            Map<String, Object> legacyPayload = new java.util.HashMap<>();
            legacyPayload.put("type", "FRIENDSHIP_REQUEST");
            legacyPayload.put("timestamp", DATE_TIME_FORMATTER.format(java.time.Instant.now()));
            legacyPayload.put("from", accountId);
            legacyPayload.put("to", friendAccountId);
            legacyPayload.put("status", "PENDING");
            xmppService.sendLegacyEvent(recipient, legacyPayload);
         */

        return Response.noContent().build();
    }

    @DELETE
    @Path("/v1/{accountId}/friends/{friendAccountId}")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.CREATE)
    public Response removeAccountFriend(@PathParam("accountId") String accountId, @PathParam("friendAccountId") String friendAccountId) throws DuplicateFriendshipException {
        if (accountId.equals(friendAccountId)) {
            throw new InvalidFriendshipWithSelfException();
        }

        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);

        // Ensure the friend's account exists before proceeding JIRA: RLTY-20
        Optional<AccountFriendAndBlockedList> friendedUserFriendsInfoOpt = friendsDAO.getFriendInfoWithAccountId(friendAccountId);
        if (friendedUserFriendsInfoOpt.isEmpty()) {
            throw new AccountDoesntExistException(friendAccountId);
        }

        // Build the XMPP Event
        Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("accountId", accountId);
        payload.put("reason", "REJECTED");
        String friendRecipient = friendAccountId + "@prod.realityfn.org";


        // If they are friends, remove the friendship.
        if (userFriendsInfo.isFriend(friendAccountId)) {
            friendsDAO.removeFriendFromUser(accountId, friendAccountId);
            friendsDAO.handleRequestFromUser(accountId, friendAccountId, true);

            xmppService.sendEvent(
                    friendRecipient,
                    "com.epicgames.friends.core.apiobjects.FriendRemoval",
                    payload
            );

            return Response.noContent().build();
        }

        // Check if there's an incoming request from the user and reject it.
        if (userFriendsInfo.hasIncomingRequestFrom(friendAccountId)) {
            friendsDAO.handleRequestFromUser(accountId, friendAccountId, false);

            xmppService.sendEvent(
                    friendRecipient,
                    "com.epicgames.friends.core.apiobjects.FriendRemoval",
                    payload
            );

            return Response.noContent().build();
        }

        // Finally check if an outgoing request from us to the friend exists and cancel it.
        if (userFriendsInfo.hasOutgoingRequestTo(friendAccountId)) {
            // Flip it around as now the handling Account ID is the friend, and we're the account requesting.
            payload.put("accountId", friendAccountId);
            xmppService.sendEvent(
                    accountId,
                    "com.epicgames.friends.core.apiobjects.FriendRemoval",
                    payload
            );

            friendsDAO.handleRequestFromUser(friendAccountId, accountId, false);
            return Response.noContent().build();
        }

        // If none of the criteria matches, return an error.
        throw new FriendshipDoesntExistException(accountId, friendAccountId);

    }

    /**
     * A helper method to find a user's friend data by their account ID,
     * or create and save a new record if one doesn't exist.
     * @param accountId The account ID to look up.
     * @return The existing or newly created AccountFriendAndBlockedList object.
     */
    private AccountFriendAndBlockedList findOrCreateUser(String accountId) {
        return friendsDAO.getFriendInfoWithAccountId(accountId).orElseGet(() -> {
            AccountFriendAndBlockedList newUser = new AccountFriendAndBlockedList(accountId);
            friendsDAO.save(newUser);
            return newUser;
        });
    }
}