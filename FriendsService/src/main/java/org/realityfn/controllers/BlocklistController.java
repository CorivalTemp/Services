package org.realityfn.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.realityfn.annotations.OAuth;
import org.realityfn.enums.Actions;
import org.realityfn.errorhandling.exceptions.common.AuthenticationFailedException;
import org.realityfn.errorhandling.exceptions.common.ServerErrorException;
import org.realityfn.errorhandling.exceptions.friends.AccountDoesntExistException;
import org.realityfn.errorhandling.exceptions.friends.CannotTargetSelfException;
import org.realityfn.errorhandling.exceptions.friends.InvalidBlockRequestWithSelfException;
import org.realityfn.errorhandling.exceptions.friends.InvalidFriendshipWithSelfException;
import org.realityfn.errorhandling.exceptions.oauth.InvalidOAuthTokenException;
import org.realityfn.models.mongo.AccountFriendAndBlockedList;
import org.realityfn.models.mongo.BlockedModel;
import org.realityfn.models.mongo.FriendsModel;
import org.realityfn.utils.XMPPEventService;
import org.realityfn.utils.database.FriendsDAO;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api")
public class BlocklistController {

    private final FriendsDAO friendsDAO;

    @Inject
    private XMPPEventService xmppService;

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public BlocklistController() {
        this.friendsDAO = new FriendsDAO();
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/v1/{accountId}/blocklist")
    @Produces("application/json")
    @OAuth(resource = "blockList:{accountId}", action = Actions.READ)
    public Response blocklist(@PathParam("accountId") String accountId) {
        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);
        List<BlockedModel> blockedUsers = userFriendsInfo.getBlockedUsers();
        if (blockedUsers == null) {
            throw new ServerErrorException();
        }
        return Response.ok(blockedUsers).build();
    }

    @POST
    @Path("/v1/{accountId}/blocklist/{blockedAccountId}")
    @Produces("application/json")
    @OAuth(resource = "blockList:{accountId}", action = Actions.UPDATE)
    public Response blockUser(@PathParam("accountId") String accountId, @PathParam("blockedAccountId") String blockedAccountId) {
        if (accountId.equals(blockedAccountId)) {
            throw new InvalidBlockRequestWithSelfException(accountId);
        }

        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);

        // Ensure the friend's account exists before proceeding JIRA: RLTY-20
        Optional<AccountFriendAndBlockedList> friendedUserFriendsInfoOpt = friendsDAO.getFriendInfoWithAccountId(blockedAccountId);
        if (friendedUserFriendsInfoOpt.isEmpty()) {
            throw new AccountDoesntExistException(blockedAccountId);
        }

        // Check if the user is already blocked
        if (userFriendsInfo.isBlocked(blockedAccountId)) {
            return Response.noContent().build();
        }

        // If they are friends, remove the friendship.
        if (userFriendsInfo.isFriend(blockedAccountId)) {
            friendsDAO.removeFriendFromUser(accountId, blockedAccountId);
        }

        // Check if there's an incoming request from the user and reject it.
        if (userFriendsInfo.hasIncomingRequestFrom(blockedAccountId)) {
            friendsDAO.handleRequestFromUser(accountId, blockedAccountId, false);
        }

        // Finally check if an outgoing request from us to the friend exists and cancel it.
        if (userFriendsInfo.hasOutgoingRequestTo(blockedAccountId)) {
            // Flip it around as now the handling Account ID is the friend, and we're the account requesting.
            friendsDAO.handleRequestFromUser(blockedAccountId, accountId, false);
        }

        friendsDAO.handlingBlockingUser(accountId, blockedAccountId, true);

        Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("ownerId", accountId);
        payload.put("accountId", blockedAccountId);

        String recipient = accountId + "@prod.realityfn.org";

        xmppService.sendEvent(
                recipient,
                "com.epicgames.friends.core.apiobjects.BlockListEntryAdded",
                payload
        );

        Map<String, Object> unaddPayload = new java.util.HashMap<>();
        unaddPayload.put("accountId", accountId);
        unaddPayload.put("reason", "REJECTED");
        String friendRecipient = blockedAccountId + "@prod.realityfn.org";
        xmppService.sendEvent(
                friendRecipient,
                "com.epicgames.friends.core.apiobjects.FriendRemoval",
                unaddPayload
        );

        return Response.noContent().build();
    }

    @DELETE
    @Path("/v1/{accountId}/blocklist/{blockedAccountId}")
    @Produces("application/json")
    @OAuth(resource = "blockList:{accountId}", action = Actions.DELETE)
    public Response unblockUser(@PathParam("accountId") String accountId, @PathParam("blockedAccountId") String blockedAccountId) throws Exception {
        if (accountId.equals(blockedAccountId)) {
            throw new CannotTargetSelfException(accountId);
        }

        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);

        // Ensure the friend's account exists before proceeding JIRA: RLTY-20
        Optional<AccountFriendAndBlockedList> friendedUserFriendsInfoOpt = friendsDAO.getFriendInfoWithAccountId(blockedAccountId);
        if (friendedUserFriendsInfoOpt.isEmpty()) {
            return Response.noContent().build();
        }
        friendsDAO.handlingBlockingUser(accountId, blockedAccountId, false);
        Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("ownerId", accountId);
        payload.put("accountId", blockedAccountId);

        String recipient = accountId + "@prod.realityfn.org";

        xmppService.sendEvent(
                recipient,
                "com.epicgames.friends.core.apiobjects.BlockListEntryRemoved",
                payload
        );
        return Response.noContent().build();
    }

    @DELETE
    @Path("/v1/{accountId}/blocklist")
    @Produces("application/json")
    @OAuth(resource = "blockList:{accountId}", action = Actions.DELETE)
    public Response clearBlockedList(@PathParam("accountId") String accountId) throws Exception {
        friendsDAO.deleteAllBlocked(accountId);

        Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("ownerId", accountId);

        String recipient = accountId + "@prod.realityfn.org";

        xmppService.sendEvent(
                recipient,
                "com.epicgames.friends.core.apiobjects.BlockListReset",
                payload
        );
        return Response.noContent().build();
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