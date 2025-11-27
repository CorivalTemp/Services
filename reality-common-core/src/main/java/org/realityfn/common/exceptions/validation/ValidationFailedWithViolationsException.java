package org.realityfn.common.exceptions.validation;

import org.realityfn.common.exceptions.BaseException;
import org.realityfn.common.exceptions.ValidationFailure;

import java.text.MessageFormat;
import java.util.Map;

public class ValidationFailedWithViolationsException extends BaseException {
    public ValidationFailedWithViolationsException(String invalidFields, Map<String, ValidationFailure> violations) {
        super("errors.com.epicgames.validation.validation_failed", MessageFormat.format("Validation Failed. Invalid fields were [{0}]", invalidFields), new String[]{"["+invalidFields+"]"}, 400, 1040, violations);
    }
}