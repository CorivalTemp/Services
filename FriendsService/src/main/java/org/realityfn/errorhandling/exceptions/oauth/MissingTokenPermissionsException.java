package org.realityfn.errorhandling.exceptions.oauth;

import org.realityfn.errorhandling.BaseException;

import static org.realityfn.enums.Actions.grabActionName;

public class MissingTokenPermissionsException extends BaseException {
    public MissingTokenPermissionsException(String resource, int action) {
        super("errors.com.epicgames.common.missing_permission", "Sorry your login does not posses the permissions '"+resource+" "+ grabActionName(action) +"' needed to perform the requested operation", 403, 1023);
        MessageVars = new String[]{resource, grabActionName(action)};
    }
}
