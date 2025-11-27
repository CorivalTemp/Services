package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class ModeratorModeNotAllowed extends BaseException {
    public ModeratorModeNotAllowed(Object... args) {
        super(
                "errors.com.epicgames.fortnite.moderator_mode_not_allowed",
                format("Account {} does not have moderator permissions", args),
                403,
                16120
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}