package org.realityfn.common.exceptions.common;

import org.realityfn.common.exceptions.BaseException;

public class ServerErrorException extends BaseException {

    public ServerErrorException() {
        super("errors.com.epicgames.common.server_error", "Sorry the server encountered an error processing your request.", 500, 1000);
    }

    public ServerErrorException(Exception e) {
        super("errors.com.epicgames.common.server_error", "Sorry the server encountered an error processing your request.", 500, 1000);
        e.printStackTrace();
    }

}
