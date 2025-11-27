package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

public class FriendshipDoesntExistException extends BaseException {
    public FriendshipDoesntExistException(String userAccountId, String friendAccountId) {
        super("errors.com.epicgames.friends.friendship_not_found", "Friendship between " + userAccountId + " and " + friendAccountId + " does not exist", 404, 14004);
        MessageVars = new String[]{userAccountId, friendAccountId};
    }
}