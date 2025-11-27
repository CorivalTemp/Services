package org.realityfn.common.services;


import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.jvnet.hk2.annotations.Service;
import org.realityfn.common.annotations.Valid;
import org.realityfn.common.interceptors.ValidationInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

@Service
public class ValidationInterceptionService implements InterceptionService
{
    private List<MethodInterceptor> interceptors = Collections.singletonList(new ValidationInterceptor());

    @Override
    public Filter getDescriptorFilter()
    {
        return BuilderHelper.allFilter();
    }

    @Override
    public List<MethodInterceptor> getMethodInterceptors(Method method)
    {
        for (Parameter parameter : method.getParameters())
        {
            if (parameter.isAnnotationPresent(Valid.class))
            {
                return interceptors;
            }
        }

        return null;
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor)
    {
        return null;
    }
}