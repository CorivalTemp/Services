package org.realityfn.fortnite.core.exceptions.modules.gamesubcatalog;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class InvalidParameterException extends BaseException {
    public InvalidParameterException(Object... args) {
        super(
                "errors.com.epicgames.modules.gamesubcatalog.invalid_parameter",
                "PurchaseCatalogEntry cannot be used for RealMoney prices. Use VerifyRealMoneyPurchase flow instead.",
                400,
                28000
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
