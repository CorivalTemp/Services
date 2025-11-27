package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

public class InvalidPayloadException extends BaseException {
    public InvalidPayloadException(String commandClass, String exception) {
        super("errors.com.epicgames.modules.profiles.invalid_payload", "Unable to parse command " + commandClass + ". " + exception, 400, 12806);
        MessageVars = new String[]{commandClass, exception};
    }
}
