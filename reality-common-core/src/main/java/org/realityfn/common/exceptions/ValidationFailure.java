package org.realityfn.common.exceptions;

import java.io.Serializable;
import java.util.Map;

public class ValidationFailure
{
    private String fieldName;

    private String errorMessage;

    private String errorCode;

    private Object invalidValue;

    private Map<String, ? extends Serializable> messageVars;

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public Object getInvalidValue()
    {
        return invalidValue;
    }

    public void setInvalidValue(Object invalidValue)
    {
        this.invalidValue = invalidValue;
    }

    public Map<String, ? extends Serializable> getMessageVars()
    {
        return messageVars;
    }

    public void setMessageVars(Map<String, ? extends Serializable> messageVars)
    {
        this.messageVars = messageVars;
    }
}