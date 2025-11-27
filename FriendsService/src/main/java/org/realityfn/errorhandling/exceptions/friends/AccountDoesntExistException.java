package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

public class AccountDoesntExistException extends BaseException {
    public AccountDoesntExistException(String friendAccountId) {
        super("errors.com.epicgames.friends.account_not_found", "Account " + friendAccountId + " does not exist", 409, 14011);
        MessageVars = new String[]{friendAccountId};
    }
}