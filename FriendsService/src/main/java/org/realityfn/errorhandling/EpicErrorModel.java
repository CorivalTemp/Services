package org.realityfn.errorhandling;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EpicErrorModel {
    @JsonProperty("errorCode")
    public String ErrorCode;

    @JsonProperty("errorMessage")
    public String ErrorMessage;

    @JsonProperty("messageVars")
    public String[] MessageVars;

    @JsonProperty("numericErrorCode")
    public int NumericErrorCode;

    @JsonProperty("originatingService")
    public String OriginatingService = "fortnite";

    @JsonProperty("intent")
    public String Intent = "prod";


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