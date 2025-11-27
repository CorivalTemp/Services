package org.realityfn.fortnite.core.models.fortnite.shop.gifts;

import org.realityfn.fortnite.core.models.fortnite.shop.requirements.Requirement;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

public class GiftInfo {
    @Field("bIsEnabled")
    private boolean isEnabled;

    @Field("forcedGiftBoxTemplateId")
    private String forcedGiftBoxTemplateId;

    @Field("purchaseRequirements")
    private List<Requirement> purchaseRequirements;

    @Field("giftRecordIds")
    private List<String> giftRecordIds;

    public GiftInfo() {
    }

    public GiftInfo(boolean isEnabled, ArrayList<Requirement> requirements) {
        this.isEnabled = isEnabled;
        this.forcedGiftBoxTemplateId = "";
        this.purchaseRequirements = requirements;
        this.giftRecordIds = new ArrayList<>();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getForcedGiftBoxTemplateId() {
        return forcedGiftBoxTemplateId;
    }

    public void setForcedGiftBoxTemplateId(String forcedGiftBoxTemplateId) {
        this.forcedGiftBoxTemplateId = forcedGiftBoxTemplateId;
    }

    public List<Requirement> getPurchaseRequirements() {
        return purchaseRequirements;
    }

    public void setPurchaseRequirements(List<Requirement> purchaseRequirements) {
        this.purchaseRequirements = purchaseRequirements;
    }

    public List<String> getGiftRecordIds() {
        return giftRecordIds;
    }

    public void setGiftRecordIds(List<String> giftRecordIds) {
        this.giftRecordIds = giftRecordIds;
    }
}