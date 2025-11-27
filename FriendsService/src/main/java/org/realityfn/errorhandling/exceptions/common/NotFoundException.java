package org.realityfn.errorhandling.exceptions.common;

import org.realityfn.errorhandling.BaseException;

public class NotFoundException extends BaseException {

    public NotFoundException() {
        super("errors.com.epicgames.common.not_found", "Sorry the resource you were trying to find could not be found", 404, 1004);
    }
}
