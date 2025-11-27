package org.realityfn.fortnite.core.models.fortnite.shop;

import org.springframework.data.mongodb.core.mapping.Field;

public class Item {
    @Field("item")
    private String item;

    @Field("name")
    private String name;

    // Default constructor for Spring Data MongoDB
    public Item() {
    }

    // Constructor for manual object creation
    public Item(String item, String name) {
        this.item = item;
        this.name = name;
    }

    // Getter and Setter methods
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
}