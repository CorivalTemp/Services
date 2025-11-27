package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidCustomKeyException extends BaseException {
    public InvalidCustomKeyException(Object... args) {
        super(
                "errors.com.epicgames.fortnite.invalid_custom_match_key",
                format("custom match key must be between {} and {} characters", args),
                400,
                16128
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}