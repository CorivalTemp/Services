package org.realityfn.fortnite.core.models.mongo;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShopMeta {

    @JsonAlias("_id")
    private String id;
    private int refreshIntervalHrs;
    private int dailyPurchaseHrs;
    private String expiration;
    private Map<String, List<String>> storefronts;

    public ShopMeta() {}

    public ShopMeta(String id, int refreshIntervalHrs, int dailyPurchaseHrs,
                    Date expiration, Map<String, List<String>> storefronts) {
        this.id = id;
        this.refreshIntervalHrs = refreshIntervalHrs;
        this.dailyPurchaseHrs = dailyPurchaseHrs;
        this.expiration = expiration.toString();
        this.storefronts = storefronts;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getRefreshIntervalHrs() { return refreshIntervalHrs; }
    public void setRefreshIntervalHrs(int refreshIntervalHrs) {
        this.refreshIntervalHrs = refreshIntervalHrs;
    }

    public int getDailyPurchaseHrs() { return dailyPurchaseHrs; }
    public void setDailyPurchaseHrs(int dailyPurchaseHrs) {
        this.dailyPurchaseHrs = dailyPurchaseHrs;
    }

    public String getExpiration() { return expiration; }
    public void setExpiration(String expiration) { this.expiration = expiration; }

    public Map<String, List<String>> getStorefronts() { return storefronts; }
    public void setStorefronts(Map<String, List<String>> storefronts) {
        this.storefronts = storefronts;
    }
}

