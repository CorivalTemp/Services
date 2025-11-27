package org.realityfn.errorhandling.catalog;

import org.realityfn.common.errorhandling.BaseException;

import java.text.MessageFormat;

public class InvalidCountException extends BaseException {
    public InvalidCountException(int count) {
        super("errors.com.epicgames.catalog.invalid_count", String.format("Sorry, the count '%s' should be greater than 0 and less than 1000.", count), 400, 5201);
        MessageVars = new String[]{String.valueOf(count)};
    }
}