package org.realityfn.common.registries.AuthModels;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

public class RouteEntry {
    public final Pattern pathPattern;
    public final Method method;
    public final List<AuthInfo> authInfo;

    // Constructors
    public RouteEntry(Pattern pathPattern, Method method, List<AuthInfo> authInfo) {
        this.pathPattern = pathPattern;
        this.method = method;
        this.authInfo = authInfo;
    }
}