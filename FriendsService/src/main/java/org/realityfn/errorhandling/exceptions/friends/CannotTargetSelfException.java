package org.realityfn.errorhandling.exceptions.friends;

import org.realityfn.errorhandling.BaseException;

import java.text.MessageFormat;

public class CannotTargetSelfException extends BaseException {
    public CannotTargetSelfException(String accountId) {
        super("errors.com.epicgames.friends.forbidden_target_yourself", MessageFormat.format("Account {0} cannot target itself.", accountId), 403, 14501);
        MessageVars = new String[]{accountId};
    }
}