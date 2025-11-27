package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidBucketIdException extends BaseException {
    public InvalidBucketIdException(String messageTemplate, Object... args) {
        super(
                "errors.com.epicgames.fortnite.invalid_bucket_id",
                format(messageTemplate, args),
                400,
                16102
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}