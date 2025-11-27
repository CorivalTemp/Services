package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidRegionException extends BaseException {
    public InvalidRegionException(Object... args) {
        super(
                "errors.com.epicgames.fortnite.invalid_region",
                format("Invalid matchmaking region: '{}'", args),
                400,
                16107
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}