package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class PlaylistUnavailableException extends BaseException {
    public PlaylistUnavailableException(String messageTemplate, Object... args) {
        super(
                "errors.com.epicgames.fortnite.playlist_unavailable",
                format(messageTemplate, args),
                400,
                16115
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}