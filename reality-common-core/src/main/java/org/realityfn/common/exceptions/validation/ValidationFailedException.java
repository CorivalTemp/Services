package org.realityfn.common.exceptions.validation;

import org.realityfn.common.exceptions.BaseException;

public class ValidationFailedException extends BaseException {
    public ValidationFailedException(String message) {
        super("errors.com.epicgames.validation.validation_failed", message, 400, 1040);
    }
}