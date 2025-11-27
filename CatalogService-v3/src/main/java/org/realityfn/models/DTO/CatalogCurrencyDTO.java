package org.realityfn.models.DTO;

import org.realityfn.enums.CurrencyType;

import java.util.List;

public class CatalogCurrencyDTO {
    private final CurrencyType type;
    private final String code;
    private final String symbol;
    private final String description;
    private final int decimals;
    private final int truncLength;
    private final List<String> priceRanges;

    // Constructor
    public CatalogCurrencyDTO(CurrencyType type, String code, String symbol, String description,
                              int decimals, int truncLength, List<String> priceRanges) {
        this.type = type;
        this.code = code;
        this.symbol = symbol;
        this.description = description;
        this.decimals = decimals;
        this.truncLength = truncLength;
        this.priceRanges = priceRanges;
    }

    // Getters
    public CurrencyType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public int getDecimals() {
        return decimals;
    }

    public int getTruncLength() {
        return truncLength;
    }

    public List<String> getPriceRanges() {
        return priceRanges;
    }
}
