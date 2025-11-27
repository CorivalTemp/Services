package org.realityfn.fortnite.core.exceptions.modules.matchmaking;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class PlayerNotRegisteredException extends BaseException {
    public PlayerNotRegisteredException(Object... args) {
        super(
                "errors.com.epicgames.modules.matchmaking.player_not_registered",
                format("Player {} is not already registered.", args),
                403,
                12107
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
