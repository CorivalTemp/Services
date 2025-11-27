package org.realityfn.fortnite.core.models.fortnite.shop.currencies;

import org.realityfn.fortnite.core.enums.CurrencyType;
import org.springframework.data.mongodb.core.mapping.Field;

public class Currency {
    @Field("currencyType")
    private CurrencyType currencyType;

    @Field("currencySubType")
    private String currencySubType;

    @Field("dynamicRegularPrice")
    private int dynamicRegularPrice;

    @Field("regularPrice")
    private int regularPrice;

    @Field("finalPrice")
    private int finalPrice;

    @Field("saleExpiration")
    private String saleExpiration;

    @Field("basePrice")
    private int basePrice;

    public Currency() {
    }

    public Currency(CurrencyType currencyType, int price) {
        this.currencyType = currencyType;

        if (currencyType == CurrencyType.MtxCurrency) this.currencySubType = null;
        else this.currencySubType = "Currency";

        this.dynamicRegularPrice = price;
        this.regularPrice = price;
        this.finalPrice = price;
        this.saleExpiration = "9999-12-31T23:59:59.999Z";
        this.basePrice = price;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public String getCurrencySubType() {
        return currencySubType;
    }

    public void setCurrencySubType(String currencySubType) {
        this.currencySubType = currencySubType;
    }

    public int getDynamicRegularPrice() {
        return dynamicRegularPrice;
    }

    public void setDynamicRegularPrice(int dynamicRegularPrice) {
        this.dynamicRegularPrice = dynamicRegularPrice;
    }

    public int getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(int regularPrice) {
        this.regularPrice = regularPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getSaleExpiration() {
        return saleExpiration;
    }

    public void setSaleExpiration(String saleExpiration) {
        this.saleExpiration = saleExpiration;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }
}