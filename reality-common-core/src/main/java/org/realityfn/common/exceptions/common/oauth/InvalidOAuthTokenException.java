package org.realityfn.common.exceptions.common.oauth;

import org.realityfn.common.exceptions.BaseException;

public class InvalidOAuthTokenException extends BaseException {
    public InvalidOAuthTokenException() {
        super("errors.com.epicgames.common.oauth.invalid_token", "The OAuthToken you are using is not valid", 401, 1014);
    }
    public InvalidOAuthTokenException(String errorMessage) {
        super("errors.com.epicgames.common.oauth.invalid_token", errorMessage, 401, 1014);
    }
    public InvalidOAuthTokenException(String errorMessage, String[] messageVars) {
        super("errors.com.epicgames.common.oauth.invalid_token", errorMessage, 401, 1014);
        MessageVars = messageVars;
    }
}