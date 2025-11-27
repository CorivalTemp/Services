package org.realityfn.common.exceptions.common.authentication;

import org.realityfn.common.exceptions.BaseException;

public class TokenVerificationFailed extends BaseException {
    public TokenVerificationFailed() {
        super("errors.com.epicgames.common.authentication.token_verification_failed", "Sorry we couldn't validate your token. Please try with a new token.", 401, 1031);
        MessageVars = new String[]{};
    }

    public TokenVerificationFailed(String authorizationHeader) {
        super("errors.com.epicgames.common.authentication.token_verification_failed", String.format("Sorry we couldn't validate your token %s. Please try with a new token.", authorizationHeader), 401, 1031);
        MessageVars = new String[]{authorizationHeader};
    }
}
