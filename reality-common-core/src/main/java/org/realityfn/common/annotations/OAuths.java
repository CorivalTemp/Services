package org.realityfn.common.annotations;

import org.realityfn.common.enums.Actions;
import org.realityfn.common.utils.PermissionCheckerImpl;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OAuths {
    OAuth[] value();
}
