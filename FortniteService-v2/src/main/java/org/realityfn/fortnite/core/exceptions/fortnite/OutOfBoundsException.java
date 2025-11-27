package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class OutOfBoundsException extends BaseException {
    public OutOfBoundsException(String messageTemplate, Object... args) {
        super(
                "errors.com.epicgames.fortnite.out_of_bounds",
                format(messageTemplate, args),
                400,
                16026
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}