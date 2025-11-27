package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;


public class InvalidProfileTypeException extends BaseException {
    public InvalidProfileTypeException(Object... args) {
        super(
                "errors.com.epicgames.modules.profiles.invalid_profile_type",
                format("Template: {} cannot be granted to profile '{}'", args),
                400,
                12807
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
