package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

public class InvalidClientRevisionsHeaderException extends BaseException {
    public InvalidClientRevisionsHeaderException() {
        super("errors.com.epicgames.modules.profiles.invalid_client_revisions_header", "Parsing client revisions header failed", 400, 12831);
    }
}
