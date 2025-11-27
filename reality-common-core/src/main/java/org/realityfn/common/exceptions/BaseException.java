package org.realityfn.common.exceptions;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseException extends RuntimeException {
    public String ErrorCode;
    public String[] MessageVars;
    public int StatusCode;
    public int NumericErrorCode;
    public String OriginatingService;
    public String Intent;
    public Map<String, ValidationFailure> violationFailed;

    @JsonAnyGetter
    public Map<String, Object> additionalFields;

    public BaseException(String message) {
        super(message);
        ErrorCode = "org.realityfn.errors.BaseException";
        MessageVars = new String[0];
        StatusCode = 500;
        NumericErrorCode = -1;
        OriginatingService = "org.realityfn.catalog.public";
        Intent = "prod";
    }

    public BaseException(String errorCode, String message, int statusCode) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = new String[0];
        StatusCode = statusCode;
        NumericErrorCode = -1;
        OriginatingService = "org.realityfn.catalog.public";
        Intent = "prod";
    }

    public BaseException(String errorCode, String message, int statusCode, int numericErrorCode) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = new String[0];
        StatusCode = statusCode;
        NumericErrorCode = numericErrorCode;
        OriginatingService = "org.realityfn.catalog.public";
        Intent = "prod";
    }

    public BaseException(String errorCode, String message, String[] messageVars, int statusCode, int numericErrorCode) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = messageVars;
        StatusCode = statusCode;
        NumericErrorCode = numericErrorCode;
        OriginatingService = "org.realityfn.catalog.public";
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

    public BaseException(String errorCode, String message, String[] messageVars, int statusCode, int numericErrorCode, Map<String, ValidationFailure> violations) {
        super(message);
        ErrorCode = errorCode;
        MessageVars = messageVars;
        StatusCode = statusCode;
        NumericErrorCode = numericErrorCode;
        OriginatingService = "party";
        Intent = "prod";
        violationFailed = violations;
    }

    public static String format(String template, Object... args) {
        if (template == null || args == null || args.length == 0) return template;
        String formatted = template;
        for (Object arg : args) {
            formatted = formatted.replaceFirst("\\{\\}", arg == null ? "null" : arg.toString());
        }
        return formatted;
    }
}