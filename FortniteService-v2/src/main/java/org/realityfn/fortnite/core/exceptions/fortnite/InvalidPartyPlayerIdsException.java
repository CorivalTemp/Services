package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidPartyPlayerIdsException extends BaseException {
    public InvalidPartyPlayerIdsException(String messageTemplate, Object... args) {
        super(
                "errors.com.epicgames.fortnite.invalid_party_player_ids",
                format(messageTemplate, args),
                400,
                16103
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}