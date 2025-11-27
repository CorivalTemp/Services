package org.realityfn.common.interceptors;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.realityfn.common.annotations.Valid;
import org.realityfn.common.exceptions.PayloadValidator;
import org.realityfn.common.exceptions.validation.ValidationFailedException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ValidationInterceptor implements MethodInterceptor
{
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        Parameter[] parameters = method.getParameters();

        for (int index = 0; index < parameters.length; index++)
        {
            Parameter parameter = parameters[index];

            if (parameter.isAnnotationPresent(Valid.class))
            {
                var showValidationFailures = parameter.getAnnotation(Valid.class).value();

                Object arg = arguments[index];
                if (arg == null)
                {
                    throw new ValidationFailedException("Sorry, it appears that your request body is empty or invalid.");
                }

                PayloadValidator.validate(arg, showValidationFailures);
            }
        }

        return invocation.proceed();
    }
}