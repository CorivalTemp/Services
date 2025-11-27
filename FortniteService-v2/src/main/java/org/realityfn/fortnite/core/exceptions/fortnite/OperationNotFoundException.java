package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

public class OperationNotFoundException extends BaseException {
    public OperationNotFoundException(String operation) {
        super("errors.com.epicgames.fortnite.operation_not_found", "Operation " + operation + " not valid", 404, 16035);
        MessageVars = new String[] {
                operation
        };
    }
}
