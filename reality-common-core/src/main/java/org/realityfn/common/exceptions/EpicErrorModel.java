package org.realityfn.common.exceptions;

import com.fasterxml.jackson.annotation.*;
import org.realityfn.common.utils.AdminAccess;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EpicErrorModel {

    @JsonIgnore
    private static final Properties props = new Properties();

    static {
        try (InputStream is = AdminAccess.class.getResourceAsStream("/common.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load common.properties", e);
        }
    }

    @JsonProperty("errorCode")
    public String ErrorCode;

    @JsonProperty("errorMessage")
    public String ErrorMessage;

    @JsonProperty("messageVars")
    public String[] MessageVars;

    @JsonProperty("numericErrorCode")
    public int NumericErrorCode;

    @JsonProperty("originatingService")
    public String OriginatingService;

    @JsonProperty("intent")
    public String Intent = props.getProperty("app.intent");

    @JsonProperty("violationFailed")
    public Map<String, ValidationFailure> violationFailed;

    @JsonAnyGetter
    public Map<String, Object> additionalFields;


    public EpicErrorModel(String errorCode, String errorMessage, String[] messageVars, int numericErrorCode, String originatingService) {
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
        MessageVars = messageVars;
        NumericErrorCode = numericErrorCode;
        OriginatingService = originatingService;
    }
    public EpicErrorModel(String errorCode, String errorMessage, int numericErrorCode, String originatingService) {
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
        NumericErrorCode = numericErrorCode;
        OriginatingService = originatingService;
    }
}