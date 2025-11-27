package org.realityfn.errorhandling.exceptions.common;

import org.realityfn.errorhandling.BaseException;

public class AuthenticationFailedException extends BaseException {
    public AuthenticationFailedException(String urlPath) {
        super("errors.com.epicgames.common.authentication.authentication_failed", "Authentication failed for " + urlPath, 400, 1032);
        MessageVars = new String[] { urlPath };
    }
}
