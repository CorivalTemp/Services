package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class ProfileRequiredException extends BaseException {
    public ProfileRequiredException(Object... args) {
        super(
                "errors.com.epicgames.fortnite.profile_required",
                format("A valid profile is required to request a MMS ticket for account {}", args),
                400,
                16142
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}