package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

public class DuplicateFriendshipException extends BaseException {
    public DuplicateFriendshipException(String userAccountId, String friendAccountId) {
        super("errors.com.epicgames.friends.duplicate_friendship", "Friendship between " + userAccountId + " and " + friendAccountId + " already exists.", 409, 14009);
        MessageVars = new String[]{userAccountId, friendAccountId};
    }
}