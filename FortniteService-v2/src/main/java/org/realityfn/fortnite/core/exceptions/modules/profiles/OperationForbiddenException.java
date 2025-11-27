package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class OperationForbiddenException extends BaseException {
    public OperationForbiddenException(String messageTemplate, Object... args) {
        super(
                "errors.com.epicgames.modules.profiles.operation_forbidden",
                format(messageTemplate, args),
                403,
                12813
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
