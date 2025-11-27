package org.realityfn.common.exceptions.common;

import org.realityfn.common.exceptions.BaseException;

public class UnsupportedMediaTypeException extends BaseException {
    public UnsupportedMediaTypeException() {
        super("errors.com.epicgames.common.unsupported_media_type", "Sorry your request could not be processed as you are supplying a media type we do not support.", 415, 1006);
    }
}
