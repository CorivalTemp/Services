package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

public class MissingActionException extends BaseException {
    public MissingActionException() {
        super("errors.com.epicgames.common.missing_action", "Login is banned or does not posses the action 'PLAY' needed to perform the requested operation for platform ''", 403, 1023); // Placeholder: add proper action and external auth method.
    }
}