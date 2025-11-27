package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;


public class ReachedMaxNumStacksException extends BaseException {
    public ReachedMaxNumStacksException(Object... args) {
        super(
                "errors.com.epicgames.modules.profiles.reached_max_num_stacks",
                format("Template: {} has reached max stack '{}' '{}' due to max stack being: {}.", args),
                400,
                12811
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
