package org.realityfn.fortnite.core.annotations;

import org.realityfn.fortnite.core.enums.ProfileRoute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfileOperation {
    String operation(); // Operation name (ex. QueryProfile)
    String[] profiles(); // Array of supported profiles (ex. {"athena", "common_core"}
    ProfileRoute[] routes(); // Array of supported routes (ex. {ProfileRoutes.CHEAT}
    boolean bypassesProfileLock() default false; // If it can write to profile while it's locked
    boolean readOnly() default false; // Is the command Read-Only? (custom impl)
    boolean nonAllowCreate() default true; // Does the command allow profile creation? (custom impl)
    boolean forceFullProfileUpdate() default false; // HACK: Testing remove this on shipping
}

