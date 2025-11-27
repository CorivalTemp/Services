package org.realityfn.fortnite.core.models.fortnite.shop;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.common.exceptions.common.ServerErrorException;
import org.realityfn.fortnite.core.models.fortnite.shop.currencies.Currency;
import org.realityfn.fortnite.core.models.fortnite.shop.gifts.GiftInfo;
import org.realityfn.fortnite.core.models.fortnite.shop.requirements.Requirement;

import java.util.ArrayList;
import java.util.List;

public class CatalogEntry {
    private String devName;

    @JsonProperty("offerId")
    @JsonAlias("_id")
    private String offerId;

    private List<String> fulfillmentIds;

    private int dailyLimit;

    private int weeklyLimit;

    private int monthlyLimit;

    private List<String> categories;

    private List<Currency> prices;

    private String matchFilter;

    private int filterWeight;

    private List<String> appStoreId;

    private List<Requirement> requirements;

    private String offerType;

    private GiftInfo giftInfo;

    private boolean refundable;

    private List<MetaInfo> metaInfo;

    private List<MetaInfo> meta;

    private String displayAssetPath;

    private String newDisplayAssetPath;

    private List<ItemGrant> itemGrants;

    private String title;

    private int sortPriority;

    private String catalogGroup;

    private int catalogGroupPriority;

    private String shortDescription;

    private String description;

    // Default constructor for Spring Data MongoDB
    public CatalogEntry() {
    }


    // Getter and Setter methods
    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public List<String> getFulfillmentIds() {
        return fulfillmentIds;
    }

    public void setFulfillmentIds(List<String> fulfillmentIds) {
        this.fulfillmentIds = fulfillmentIds;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public int getWeeklyLimit() {
        return weeklyLimit;
    }

    public void setWeeklyLimit(int weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    public int getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(int monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Currency> getPrices() {
        return prices;
    }

    public void setPrices(List<Currency> prices) {
        this.prices = prices;
    }

    public String getMatchFilter() {
        return matchFilter;
    }

    public void setMatchFilter(String matchFilter) {
        this.matchFilter = matchFilter;
    }

    public int getFilterWeight() {
        return filterWeight;
    }

    public void setFilterWeight(int filterWeight) {
        this.filterWeight = filterWeight;
    }

    public List<String> getAppStoreId() {
        return appStoreId;
    }

    public void setAppStoreId(List<String> appStoreId) {
        this.appStoreId = appStoreId;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public GiftInfo getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(GiftInfo giftInfo) {
        this.giftInfo = giftInfo;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public List<MetaInfo> getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(List<MetaInfo> metaInfo) {
        this.metaInfo = metaInfo;
    }

    public List<MetaInfo> getMeta() {
        return meta;
    }

    public void setMeta(List<MetaInfo> meta) {
        this.meta = meta;
    }

    public String getDisplayAssetPath() {
        return displayAssetPath;
    }

    public void setDisplayAssetPath(String displayAssetPath) {
        this.displayAssetPath = displayAssetPath;
    }

    public String getNewDisplayAssetPath() {
        return newDisplayAssetPath;
    }

    public void setNewDisplayAssetPath(String newDisplayAssetPath) {
        this.newDisplayAssetPath = newDisplayAssetPath;
    }

    public List<ItemGrant> getItemGrants() {
        return itemGrants;
    }

    public void setItemGrants(List<ItemGrant> itemGrants) {
        this.itemGrants = itemGrants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    public int getCatalogGroupPriority() {
        return catalogGroupPriority;
    }

    public void setCatalogGroupPriority(int catalogGroupPriority) {
        this.catalogGroupPriority = catalogGroupPriority;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatalogGroup() {
        return catalogGroup;
    }

    public void setCatalogGroup(String catalogGroup) {
        this.catalogGroup = catalogGroup;
    }
}