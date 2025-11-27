package org.realityfn.fortnite.core.exceptions.cloudstorage;

import org.realityfn.common.exceptions.BaseException;

public class CloudstorageSystemFileAlreadyExistsException extends BaseException {
    public CloudstorageSystemFileAlreadyExistsException() {
        super("errors.com.epicgames.common.mongo_write_error", "Sorry, there was an error writing to the database.", 409, 1060);
    }
}
