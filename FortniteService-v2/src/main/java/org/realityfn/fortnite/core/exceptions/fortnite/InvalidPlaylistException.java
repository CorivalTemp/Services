package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidPlaylistException extends BaseException {
    public InvalidPlaylistException(Object... args) {
        super(
                "errors.com.epicgames.fortnite.invalid_playlist",
                format("Invalid playlist: '{}'", args),
                400,
                16106
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}