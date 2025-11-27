package org.realityfn.errorhandling.catalog;

import org.realityfn.common.errorhandling.BaseException;

import java.text.MessageFormat;

public class InvalidStartException extends BaseException {
    public InvalidStartException(int start) {
        super("errors.com.epicgames.catalog.invalid_start", String.format("Sorry, the start '%s' should be positive.", (start)), 400, 5208);
        MessageVars = new String[]{String.valueOf(start)};
    }
}