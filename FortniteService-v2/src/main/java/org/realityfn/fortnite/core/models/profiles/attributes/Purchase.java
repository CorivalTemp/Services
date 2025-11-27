package org.realityfn.fortnite.core.models.profiles.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.fortnite.core.models.profiles.notifications.LootItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Purchase {
    public List<String> fulfillments;
    public String purchaseDate;
    public Map<String, String> metadata;
    public int totalMtxPaid;
    public String purchaseId;
    public List<MtxDetail> mtxDetails;
    public String offerId;
    public boolean freeRefundEligible;
    public List<LootItem> lootResult;
    public String gameContext;
    public String refundDate;

    public Purchase() {
    }

    public Purchase(String purchaseId, String offerId, String purchaseDate, int totalMtxPaid) {
        this.purchaseId = purchaseId;
        this.offerId = offerId;
        this.purchaseDate = purchaseDate;
        this.totalMtxPaid = totalMtxPaid;
        this.freeRefundEligible = false;
    }

    public Purchase(String purchaseId, String offerId, String purchaseDate,
                    int totalMtxPaid, List<String> fulfillments, boolean freeRefundEligible) {
        this.purchaseId = purchaseId;
        this.offerId = offerId;
        this.purchaseDate = purchaseDate;
        this.totalMtxPaid = totalMtxPaid;
        this.fulfillments = fulfillments;
        this.freeRefundEligible = freeRefundEligible;
    }

    public Purchase(String purchaseId, String offerId, String purchaseDate,
                    int totalMtxPaid, String gameContext, boolean freeRefundEligible,
                    List<LootItem> lootResult, Map<String, String> metadata,
                    List<MtxDetail> mtxDetails) {
        this.purchaseId = purchaseId;
        this.offerId = offerId;
        this.purchaseDate = purchaseDate;
        this.totalMtxPaid = totalMtxPaid;
        this.gameContext = gameContext;
        this.freeRefundEligible = freeRefundEligible;
        this.lootResult = lootResult;
        this.metadata = metadata;
        this.mtxDetails = mtxDetails;
        this.fulfillments = new ArrayList<>();
    }

    public Purchase(List<String> fulfillments, String purchaseDate, Map<String, String> metadata,
                    int totalMtxPaid, String purchaseId, List<MtxDetail> mtxDetails,
                    String offerId, boolean freeRefundEligible, List<LootItem> lootResult,
                    String gameContext, String refundDate) {
        this.fulfillments = fulfillments;
        this.purchaseDate = purchaseDate;
        this.metadata = metadata;
        this.totalMtxPaid = totalMtxPaid;
        this.purchaseId = purchaseId;
        this.mtxDetails = mtxDetails;
        this.offerId = offerId;
        this.freeRefundEligible = freeRefundEligible;
        this.lootResult = lootResult;
        this.gameContext = gameContext;
        this.refundDate = refundDate;
    }

    public static class Builder {
        private Purchase purchase = new Purchase();

        public Builder purchaseId(String purchaseId) {
            purchase.purchaseId = purchaseId;
            return this;
        }

        public Builder offerId(String offerId) {
            purchase.offerId = offerId;
            return this;
        }

        public Builder purchaseDate(String purchaseDate) {
            purchase.purchaseDate = purchaseDate;
            return this;
        }

        public Builder totalMtxPaid(int totalMtxPaid) {
            purchase.totalMtxPaid = totalMtxPaid;
            return this;
        }

        public Builder fulfillments(List<String> fulfillments) {
            purchase.fulfillments = fulfillments;
            return this;
        }

        public Builder metadata(Map<String, String> metadata) {
            purchase.metadata = metadata;
            return this;
        }

        public Builder mtxDetails(List<MtxDetail> mtxDetails) {
            purchase.mtxDetails = mtxDetails;
            return this;
        }

        public Builder freeRefundEligible(boolean freeRefundEligible) {
            purchase.freeRefundEligible = freeRefundEligible;
            return this;
        }

        public Builder lootResult(List<LootItem> lootResult) {
            purchase.lootResult = lootResult;
            return this;
        }

        public Builder refundDate(String refundDate) {
            purchase.refundDate = refundDate;
            return this;
        }

        public Builder gameContext(String gameContext) {
            purchase.gameContext = gameContext;
            return this;
        }

        public Purchase build() {
            return purchase;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}