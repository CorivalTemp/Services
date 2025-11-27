package org.realityfn.common.exceptions.common.oauth;

import org.realityfn.common.exceptions.BaseException;

import static org.realityfn.common.enums.Actions.grabActionName;

public class MissingPermissionException extends BaseException {
    public MissingPermissionException(String resource, int action) {
        super("errors.com.epicgames.common.missing_permission", "Sorry your login does not posses the permissions '"+resource+" "+ grabActionName(action) +"' needed to perform the requested operation", 403, 1023);
        MessageVars = new String[]{resource, grabActionName(action)};
    }
}
