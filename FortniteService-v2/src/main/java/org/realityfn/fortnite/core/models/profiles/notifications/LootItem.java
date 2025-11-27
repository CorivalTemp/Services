package org.realityfn.fortnite.core.models.profiles.notifications;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LootItem {
    public String itemGuid;
    public String itemProfile;
    public String itemType;
    public int quantity;

    public LootItem() {}

    public LootItem(String itemGuid, String itemProfile, String itemType, int quantity) {
        this.itemGuid = itemGuid;
        this.itemProfile = itemProfile;
        this.itemType = itemType;
        this.quantity = quantity;
    }
}
