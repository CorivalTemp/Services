package org.realityfn.fortnite.core.exceptions.modules.matchmaking;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class UnknownSessionException extends BaseException {
    public UnknownSessionException(Object... args) {
        super(
                "errors.com.epicgames.modules.matchmaking.unknown_session",
                format("unknown session id {}", args),
                404,
                12101
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
