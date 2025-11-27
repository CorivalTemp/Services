package org.realityfn.common.exceptions.common;

import org.realityfn.common.exceptions.BaseException;

public class MongoExecutionTimeoutError extends BaseException {
    public MongoExecutionTimeoutError() {
        super("errors.com.epicgames.common.mongo_execution_timeout_error", "Sorry, there was a timeout utilizing the database.", 500, 1045);
    }
}
