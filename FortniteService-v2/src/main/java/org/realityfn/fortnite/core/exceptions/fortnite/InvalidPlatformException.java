package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidPlatformException extends BaseException {
    public InvalidPlatformException(Object... args) {
        super(
                "errors.com.epicgames.fortnite.invalid_platform",
                format("Unrecognized claimed platform: '{}'", args),
                400,
                16104
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}