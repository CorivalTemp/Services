package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;


public class ProfileWriteLockedException extends BaseException {
    public ProfileWriteLockedException(Object... args) {
        super(
                "errors.com.epicgames.modules.profiles.profile_write_locked",
                format("Attempting to write while profile is locked. MISSINGLOCK({}:{})", args),
                403,
                12821
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}