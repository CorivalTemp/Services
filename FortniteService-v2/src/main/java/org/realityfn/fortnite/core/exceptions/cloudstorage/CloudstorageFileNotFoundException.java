package org.realityfn.fortnite.core.exceptions.cloudstorage;

import org.realityfn.common.exceptions.BaseException;

public class CloudstorageFileNotFoundException extends BaseException {
    public CloudstorageFileNotFoundException(String uniqueFilename) {
        super("errors.com.epicgames.cloudstorage.file_not_found", "Sorry, we couldn't find a system file for " + uniqueFilename, 404, 7001);
        MessageVars = new String[] { uniqueFilename };
    }
    public CloudstorageFileNotFoundException(String uniqueFilename, String accountId) {
        super("errors.com.epicgames.cloudstorage.file_not_found", "Sorry, we couldn't find a file for " + uniqueFilename + " for account " + accountId, 404, 12007);
        MessageVars = new String[] { uniqueFilename, accountId };
    }
}
