package org.realityfn.fortnite.core.exceptions.modules.gamesubcatalog;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class CatalogOutOfDateException extends BaseException {
    public CatalogOutOfDateException(Object... args) {
        super(
                "errors.com.epicgames.modules.gamesubcatalog.catalog_out_of_date",
                format("Offer {} is not valid or not for sale currently.", args),
                400,
                28004
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
