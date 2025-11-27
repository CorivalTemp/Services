package org.realityfn.fortnite.core.models.profiles.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class PurchaseHistory {
    private int refundsUsed;
    private List<Purchase> purchases;
    private int refundCredits;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String tokenRefreshReferenceTime;

    public PurchaseHistory() {}

    public PurchaseHistory(int refundsUsed, List<Purchase> purchases, int refundCredits) {
        this.purchases = purchases;
        this.refundCredits = refundCredits;
        this.refundsUsed = refundsUsed;
    }

    public int getRefundsUsed() {
        return refundsUsed;
    }

    public void setRefundsUsed(int refundsUsed) {
        this.refundsUsed = refundsUsed;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public int getRefundCredits() {
        return refundCredits;
    }

    public void setRefundCredits(int refundCredits) {
        this.refundCredits = refundCredits;
    }

    public String getTokenRefreshReferenceTime() {
        return tokenRefreshReferenceTime;
    }

    public void setTokenRefreshReferenceTime(String tokenRefreshReferenceTime) {
        this.tokenRefreshReferenceTime = tokenRefreshReferenceTime;
    }
}
