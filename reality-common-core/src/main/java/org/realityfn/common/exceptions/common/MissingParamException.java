package org.realityfn.common.exceptions.common;

import org.realityfn.common.exceptions.BaseException;

public class MissingParamException extends BaseException {
    public MissingParamException() {
        super("errors.com.epicgames.common.missing_param", "One or more required parameters is null", 400, -1);
    }
}
