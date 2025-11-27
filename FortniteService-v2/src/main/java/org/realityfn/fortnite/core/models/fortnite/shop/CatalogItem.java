package org.realityfn.fortnite.core.models.fortnite.shop;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

public class CatalogItem {
    @Field("section")
    private String section;

    @Field("id")
    private String id;

    @Field("item")
    private String item;

    @Field("name")
    private String name;

    @Field("items")
    private List<Item> items;

    @Field("price")
    private int price;

    @Field("rarity")
    private String rarity;

    @Field("displayAssetPath")
    private String displayAssetPath;

    @Field("metaInfo")
    private List<MetaInfo> metaInfo;

    public CatalogItem() {
    }

    public CatalogItem(String section, String id, String item, String name, ArrayList<Item> extraItems, int price, String rarity, String displayAssetPath, ArrayList<MetaInfo> metaInfo) {
        this.section = section;
        this.id = "v2:/" + id;
        this.item = item;
        this.name = name;
        this.items = extraItems;
        this.price = price;
        this.rarity = rarity;
        this.displayAssetPath = displayAssetPath;
        this.metaInfo = metaInfo;
    }

    // Getter and Setter methods
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getDisplayAssetPath() {
        return displayAssetPath;
    }

    public void setDisplayAssetPath(String displayAssetPath) {
        this.displayAssetPath = displayAssetPath;
    }

    public List<MetaInfo> getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(List<MetaInfo> metaInfo) {
        this.metaInfo = metaInfo;
    }
}