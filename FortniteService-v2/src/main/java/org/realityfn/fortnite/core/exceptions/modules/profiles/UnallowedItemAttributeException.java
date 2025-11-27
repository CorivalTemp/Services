package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;


public class UnallowedItemAttributeException extends BaseException {
    public UnallowedItemAttributeException(Object... args) {
        super(
                "errors.com.epicgames.modules.profiles.unallowed_item_attribute",
                format("Attribute {} is not allowed to be set for template '{}'", args), //
                400,
                12810
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
