package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

import java.text.MessageFormat;

public class RequestAlreadySentToAccountException extends BaseException {
    public RequestAlreadySentToAccountException(String friendAccountId) {
        super("errors.com.epicgames.friends.friend_request_already_sent", MessageFormat.format("Friendship request has already been sent to {0}", friendAccountId), 409, 14014);
        MessageVars = new String[]{friendAccountId};
    }
}