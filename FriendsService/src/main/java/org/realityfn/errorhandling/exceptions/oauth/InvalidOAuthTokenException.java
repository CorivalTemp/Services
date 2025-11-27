package org.realityfn.errorhandling.exceptions.oauth;

import org.realityfn.errorhandling.BaseException;

public class InvalidOAuthTokenException extends BaseException {
    public InvalidOAuthTokenException() {
        super("errors.com.epicgames.common.oauth.invalid_token", "The OAuthToken you are using is not valid", 401, 1014);
    }
}