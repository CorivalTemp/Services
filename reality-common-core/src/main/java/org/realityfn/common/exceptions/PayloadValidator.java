package org.realityfn.common.exceptions;


import com.fasterxml.jackson.annotation.JsonProperty;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.ValidationFailedException;
import org.realityfn.common.exceptions.validation.ValidationFailedWithViolationsException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayloadValidator
{
    private static final Validator VALIDATOR = new Validator();

    public static void validate(Object obj, boolean showValidationFailures) throws ValidationFailedException
    {
        List<ConstraintViolation> violations = VALIDATOR.validate(obj);

        if (!violations.isEmpty())
        {
            Map<String, ValidationFailure> validationFailures = new HashMap<>();
            List<String> invalidFields = new ArrayList<>();

            for (ConstraintViolation violation : violations)
            {
                String violationMsg = violation.getMessage();

                String message = violationMsg.substring(violation.getContextPathAsString().length());

                String invalidParameterName = getString(obj, violation, violationMsg);

                if (invalidFields.contains(invalidParameterName)) continue;

                invalidFields.add(invalidParameterName);

                if (showValidationFailures)
                {

                    validationFailures.put(invalidParameterName, new ValidationFailure()
                    {{
                        setFieldName(invalidParameterName);
                        setErrorMessage(invalidParameterName + message);
                        setErrorCode(violation.getErrorCode());
                        setInvalidValue(violation.getInvalidValue());
                        setMessageVars(violation.getMessageVariables() != null ? violation.getMessageVariables() : new HashMap<>());
                    }});
                }
            }

            throw new ValidationFailedWithViolationsException(String.join(", ", invalidFields), validationFailures);
        }
    }

    private static String getString(Object obj, ConstraintViolation violation, String violationMsg) {
        String invalidParameterName = violationMsg.substring(obj.getClass().getName().length() + 1, violationMsg.indexOf(" "));
        try
        {
            OValContext context = violation.getCheckDeclaringContext();
            Class<?> declaringClass = context.getDeclaringClass();
            Field field = declaringClass.getDeclaredField(invalidParameterName);

            JsonProperty jsonAnnotation = field.getAnnotation(JsonProperty.class);
            if (jsonAnnotation != null)
            {
                invalidParameterName = jsonAnnotation.value();
            }
        }
        catch (Exception ignored) { }
        return invalidParameterName;
    }
}