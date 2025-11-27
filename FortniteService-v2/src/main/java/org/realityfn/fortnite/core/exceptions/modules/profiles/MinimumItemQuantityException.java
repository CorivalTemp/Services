package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;


public class MinimumItemQuantityException extends BaseException {
    public MinimumItemQuantityException(Object... args) {
        super(
                "errors.com.epicgames.modules.profiles.minimum_item_quantity",
                format("Quantity {} is lower than the minimum {}.", args),
                400,
                12809
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
