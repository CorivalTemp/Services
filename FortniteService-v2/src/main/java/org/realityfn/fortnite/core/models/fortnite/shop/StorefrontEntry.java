package org.realityfn.fortnite.core.models.fortnite.shop;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class StorefrontEntry {

    @Field("name")
    private String name;

    @Field("catalogEntries")
    private List<CatalogEntry> catalogEntries;

    // Default constructor for Spring Data MongoDB
    public StorefrontEntry() {
    }

    // Constructor for manual object creation
    public StorefrontEntry(String name, List<CatalogEntry> catalogEntries) {
        this.name = name;
        this.catalogEntries = catalogEntries;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CatalogEntry> getCatalogEntries() {
        return catalogEntries;
    }

    public void setCatalogEntries(List<CatalogEntry> catalogEntries) {
        this.catalogEntries = catalogEntries;
    }
}