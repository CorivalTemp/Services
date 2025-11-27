package org.realityfn.fortnite.core.models.profiles.attributes;

import org.realityfn.fortnite.core.enums.AppStore;

import java.util.List;
import java.util.Map;

public class InAppPurchases {
    public List<String> receipts;
    public Map<String, Integer> fulfillmentCounts;
    public Map<AppStore, StoreTimer> refreshTimers;
    public List<String> subscriptionEndActions;
    public List<String> ignoredReceipts;
    public List<String> rewardsGivenIds;
    public int version;
}
