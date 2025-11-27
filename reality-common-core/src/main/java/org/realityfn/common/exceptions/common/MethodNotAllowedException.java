package org.realityfn.common.exceptions.common;

import org.realityfn.common.exceptions.BaseException;

public class MethodNotAllowedException extends BaseException {
    public MethodNotAllowedException() {
        super("errors.com.epicgames.common.method_not_allowed", "Sorry the resource you were trying to access cannot be accessed with the HTTP method you used.", 405, 1009);
    }
}
