package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class TemplateNotFound extends BaseException {
    public TemplateNotFound(Object... args) {
        super(
                "errors.com.epicgames.modules.profile.template_not_found",
                format("Template {} missing or invalid.", args),
                400,
                12805
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
