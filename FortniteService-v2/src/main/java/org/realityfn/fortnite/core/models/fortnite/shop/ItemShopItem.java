package org.realityfn.fortnite.core.models.fortnite.shop;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class ItemShopItem {
    @Field("name")
    private String name;

    @Field("id")
    private String id;

    @Field("rarity")
    private String rarity;

    @Field("displayAsset")
    private String displayAsset;

    @Field("price")
    private int price;

    @Field("attachedItems")
    private List<Item> attachedItems;

    // Default constructor for Spring Data MongoDB
    public ItemShopItem() {
    }

    // Constructor for manual object creation
    public ItemShopItem(String name, String id, String rarity, String displayAsset, int price, List<Item> attachedItems) {
        this.name = name;
        this.id = id;
        this.rarity = rarity;
        this.displayAsset = displayAsset;
        this.price = price;
        this.attachedItems = attachedItems;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getDisplayAsset() {
        return displayAsset;
    }

    public void setDisplayAsset(String displayAsset) {
        this.displayAsset = displayAsset;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Item> getAttachedItems() {
        return attachedItems;
    }

    public void setAttachedItems(List<Item> attachedItems) {
        this.attachedItems = attachedItems;
    }
}