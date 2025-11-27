package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

import java.text.MessageFormat;

public class InvalidBlockRequestWithSelfException extends BaseException {
    public InvalidBlockRequestWithSelfException(String accountId) {
        super("errors.com.epicgames.friends.invalid_block_request", MessageFormat.format("Account {0} cannot block itself.", accountId), 400, 14013);
        MessageVars = new String[]{accountId};
    }
}