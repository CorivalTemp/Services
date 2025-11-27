package org.realityfn.errorhandling.catalog;

import org.realityfn.common.errorhandling.BaseException;

import java.text.MessageFormat;

public class CurrencyNotFoundException extends BaseException {
    public CurrencyNotFoundException(String currencyCode) {
        super("errors.com.epicgames.catalog.currency_not_found", String.format("Sorry, we couldn't find a currency for the code '%s'.", currencyCode), 404, 5103);
        MessageVars = new String[]{currencyCode};
    }
}