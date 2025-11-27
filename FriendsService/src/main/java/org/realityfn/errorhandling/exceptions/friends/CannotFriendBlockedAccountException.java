package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

import java.text.MessageFormat;

public class CannotFriendBlockedAccountException extends BaseException {
    public CannotFriendBlockedAccountException(String accountId, String friendAccountId) {
        super("errors.com.epicgames.friends.cannot_friend_blocked_account", MessageFormat.format("Account {0} cannot friend {1}, as one party has the other blocked.", accountId, friendAccountId), 403, 14011);
        MessageVars = new String[]{accountId, friendAccountId};
    }
}