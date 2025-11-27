package org.realityfn.fortnite.core.models.profiles.notifications;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LootResult {
    public List<LootItem> items;

    public LootResult() {}

    public LootResult(List<LootItem> items) {
        this.items = items;
    }
}
