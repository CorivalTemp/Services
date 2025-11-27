package org.realityfn.models.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.BsonObjectId;
import org.bson.codecs.pojo.annotations.BsonId;
import org.realityfn.enums.CurrencyType;

import java.util.List;

public class CatalogCurrencyMongo {

    @JsonProperty(value = "_id", access = JsonProperty.Access.WRITE_ONLY)
    @BsonId
    private String id;

    private CurrencyType type;
    private String code;
    private String symbol;
    private String description;
    private Integer decimals;
    private Integer truncLength;
    private List<String> priceRanges;

    // Default constructor
    public CatalogCurrencyMongo() {}

    // Constructor with all fields
    public CatalogCurrencyMongo(CurrencyType type, String code, String symbol, String description,
                                Integer decimals, Integer truncLength, List<String> priceRanges) {
        this.type = type;
        this.code = code;
        this.symbol = symbol;
        this.description = description;
        this.decimals = decimals;
        this.truncLength = truncLength;
        this.priceRanges = priceRanges;
    }

    // Getters and Setters
    public CurrencyType getType() {
        return type;
    }

    public void setType(CurrencyType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public Integer getTruncLength() {
        return truncLength;
    }

    public void setTruncLength(Integer truncLength) {
        this.truncLength = truncLength;
    }

    public List<String> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<String> priceRanges) {
        this.priceRanges = priceRanges;
    }
}