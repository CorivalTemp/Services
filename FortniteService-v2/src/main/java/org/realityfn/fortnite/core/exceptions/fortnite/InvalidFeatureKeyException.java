package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

public class InvalidFeatureKeyException extends BaseException {
    public InvalidFeatureKeyException() {
        super("errors.com.epicgames.fortnite.invalid_featurekey_request", "Account access tier does not include any client feature keys", 400, -1);
    }
}