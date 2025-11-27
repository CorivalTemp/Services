package org.realityfn.fortnite.core.models.profiles.notifications;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {
    public String type;
    public boolean primary;
    public LootResult lootResult;

    public Notification() {}

    public Notification(String type, boolean primary, LootResult lootResult) {
        this.type = type;
        this.primary = primary;
        this.lootResult = lootResult;
    }

    // Static factory method for CatalogPurchase
    public static Notification catalogPurchase(LootResult lootResult, boolean primary) {
        return new Notification("CatalogPurchase", primary, lootResult);
    }
}
