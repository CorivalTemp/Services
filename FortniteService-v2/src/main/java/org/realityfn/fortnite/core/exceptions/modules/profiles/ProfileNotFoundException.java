package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;


public class ProfileNotFoundException extends BaseException {
    public ProfileNotFoundException(Object... args) {
        super(
                "errors.com.epicgames.modules.profiles.profile_not_found",
                format("Read-only (true) or non-allow-create (! true) operation could not be applied to account {} profile {} because it was not found.", args),
                404,
                12804
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
