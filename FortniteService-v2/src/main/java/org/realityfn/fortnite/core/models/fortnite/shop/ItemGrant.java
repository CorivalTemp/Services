package org.realityfn.fortnite.core.models.fortnite.shop;

import org.springframework.data.mongodb.core.mapping.Field;

public class ItemGrant {
    @Field("templateId")
    private String templateId;

    @Field("quantity")
    private int quantity;

    public ItemGrant() {
    }

    public ItemGrant(String templateId, int quantity) {
        this.templateId = templateId;
        this.quantity = quantity;
    }

    // Getter and Setter methods
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}