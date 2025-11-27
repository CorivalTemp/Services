package org.realityfn.fortnite.core.exceptions.modules.gameplayutils;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class NotEnoughMtxException extends BaseException {
    public NotEnoughMtxException(Object... args) {
        super(
                "errors.com.epicgames.modules.gameplayutils.not_enough_mtx",
                format("Purchase: {}: Required {} MTX but account balance is only {}.", args),
                400,
                12720
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
