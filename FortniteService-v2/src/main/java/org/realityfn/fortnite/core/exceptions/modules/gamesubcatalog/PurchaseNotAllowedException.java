package org.realityfn.fortnite.core.exceptions.modules.gamesubcatalog;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class PurchaseNotAllowedException extends BaseException {
    public PurchaseNotAllowedException(String messageTemplate, Object... args) {
        super(
                "errors.com.epicgames.modules.gamesubcatalog.purchase_not_allowed",
                format(messageTemplate),
                400,
                28004
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
