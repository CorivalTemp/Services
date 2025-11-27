package org.realityfn.fortnite.core.models.profiles.attributes;

public class MtxDetail {
    public int quantity;
    public String templateId;
    public String platform;

    public MtxDetail() {
    }

    public MtxDetail(int quantity, String templateId, String platform) {
        this.quantity = quantity;
        this.templateId = templateId;
        this.platform = platform;
    }
}
