package org.realityfn.common.annotations;

import org.realityfn.common.enums.Actions;
import org.realityfn.common.utils.PermissionCheckerImpl;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OAuths.class)
public @interface OAuth {
    String resource() default "null"; // the required scope
    Actions action() default Actions.NONE; // read, write, etc
}
