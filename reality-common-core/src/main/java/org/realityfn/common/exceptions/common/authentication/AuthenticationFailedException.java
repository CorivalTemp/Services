package org.realityfn.common.exceptions.common.authentication;

import org.realityfn.common.exceptions.BaseException;

public class AuthenticationFailedException extends BaseException {
    public AuthenticationFailedException(String urlPath) {
        super("errors.com.epicgames.common.authentication.authentication_failed", "Authentication failed for " + urlPath, 400, 1032);
        MessageVars = new String[] { urlPath };
    }
}
