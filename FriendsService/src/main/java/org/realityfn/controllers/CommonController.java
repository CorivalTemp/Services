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
public class CommonController {

    private final FriendsDAO friendsDAO;

    @Inject
    private XMPPEventService xmppService;

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public CommonController() {
        this.friendsDAO = new FriendsDAO();
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/v1/{accountId}/summary")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.READ)
    public Response getAccountSummary(@PathParam("accountId") String accountId) {

        AccountFriendAndBlockedList userFriendsInfo = findOrCreateUser(accountId);
        return Response.ok(convertToSummaryPayload(userFriendsInfo)).build();
    }

    /**
     *   Deprecated endpoint (so we'll return an empty array.)
     */
    @GET
    @Path("/v1/{accountId}/suggested")
    @Produces("application/json")
    @OAuth(resource = "friends:{accountId}", action = Actions.READ)
    public Response getAccountSuggested(@PathParam("accountId") String accountId) {

        // we'll still register the user for this call incase it's ever the first endpoint to be called.
        findOrCreateUser(accountId);
        return Response.ok(List.of()).build();
    }

    public AccountSummaryModel convertToSummaryPayload(AccountFriendAndBlockedList userFriendsInfo) {
        return new AccountSummaryModel(
                userFriendsInfo.getCurrentFriends(),
                userFriendsInfo.getPendingRequests(),
                userFriendsInfo.getSentRequests(),
                userFriendsInfo.getBlockedUsers(),
                userFriendsInfo.getSettings()
        );
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