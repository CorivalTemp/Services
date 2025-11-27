package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.text.MessageFormat;

public class InvalidCommandException extends BaseException {
    public InvalidCommandException(String command, String profileTemplate, String profileId) {
        super("errors.com.epicgames.modules.profiles.invalid_command", MessageFormat.format("{0} is not valid on {1} profiles ({2})", command, profileTemplate, profileId), 400, 12801);
        MessageVars = new String[]{command, profileTemplate, profileId};

    }
}
