package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidUserAgentException extends BaseException {
    public InvalidUserAgentException(Object... args) {
        super(
                "errors.com.epicgames.fortnite.invalid_user_agent",
                format("UserAgent value: '{}' does not match regex: '{}'", args),
                400,
                16195
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}