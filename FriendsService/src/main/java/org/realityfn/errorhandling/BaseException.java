package org.realityfn.errorhandling;

public class BaseException extends RuntimeException {
    public String ErrorCode;
    public String[] MessageVars;
    public int StatusCode;
    public int NumericErrorCode;
    public String OriginatingService;
    public String Intent;

    public BaseException(String message) {
        super(message);
        ErrorCode = "org.realityfn.errors.BaseException";
        MessageVars = new String[0];
        StatusCode = 500;
        NumericErrorCode = -1;
        OriginatingService = "friends";
        Intent = "prod";
    }

    public BaseException(String errorCode, String message, int statusCode) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = new String[0];
        StatusCode = statusCode;
        NumericErrorCode = -1;
        OriginatingService = "friends";
        Intent = "prod";
    }

    public BaseException(String errorCode, String message, int statusCode, int numericErrorCode) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = new String[0];
        StatusCode = statusCode;
        NumericErrorCode = numericErrorCode;
        OriginatingService = "friends";
        Intent = "prod";
    }

    public BaseException(String errorCode, String message, String[] messageVars, int statusCode, int numericErrorCode) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = messageVars;
        StatusCode = statusCode;
        NumericErrorCode = numericErrorCode;
        OriginatingService = "friends";
        Intent = "prod";
    }

    public BaseException(String errorCode, String message, String[] messageVars, int statusCode, int numericErrorCode, String originatingService, String intent) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = messageVars;
        StatusCode = statusCode;
        NumericErrorCode = numericErrorCode;
        OriginatingService = originatingService;
        Intent = intent;
    }
}