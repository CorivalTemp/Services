package org.realityfn.registries.AuthModels;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class RouteEntry {
    public final Pattern pathPattern;
    public final Method method;
    public final AuthInfo authInfo;

    // Constructors
    public RouteEntry(Pattern pathPattern, Method method, AuthInfo authInfo) {
        this.pathPattern = pathPattern;
        this.method = method;
        this.authInfo = authInfo;
    }
}