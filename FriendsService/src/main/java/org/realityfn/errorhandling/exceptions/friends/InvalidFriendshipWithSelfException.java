package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

public class InvalidFriendshipWithSelfException extends BaseException {
    public InvalidFriendshipWithSelfException() {
        super("errors.com.epicgames.friends.invalid_friendship", "Users cannot have social relations with themselves.", 400, 14001);
    }
}