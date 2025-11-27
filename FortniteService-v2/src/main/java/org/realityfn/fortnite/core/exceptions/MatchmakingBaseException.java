package org.realityfn.fortnite.core.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.common.exceptions.BaseException;
import org.realityfn.common.exceptions.ValidationFailure;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MatchmakingBaseException extends BaseException {

    public MatchmakingBaseException(String errorCode, String message, int statusCode, int numericErrorCode, long banDaysRemaining) {
        super(errorCode, message, statusCode, numericErrorCode);
        this.additionalFields = new HashMap<>();
        this.additionalFields.put("banDaysRemaining", banDaysRemaining);
    }
}
