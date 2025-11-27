package org.realityfn.errorhandling.exceptions.common;

import org.realityfn.errorhandling.BaseException;

public class ServerErrorException extends BaseException {

    public ServerErrorException() {
        super("errors.com.epicgames.common.server_error", "Sorry the server encountered an error processing your request.", 500, 1000);
    }

    public ServerErrorException(Exception e) {
        super("errors.com.epicgames.common.server_error", "Sorry the server encountered an error processing your request.", 500, 1000);
        e.printStackTrace();
    }

}
