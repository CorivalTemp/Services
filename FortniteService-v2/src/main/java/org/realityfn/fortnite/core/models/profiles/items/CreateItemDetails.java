package org.realityfn.fortnite.core.models.profiles.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.util.HashMap;
import java.util.List;

public class CreateItemDetails {

    @NotNull
    @NotBlank
    private String templateId;

    private HashMap<String, Object> attributes = new HashMap<>();

    @Min(1)
    private int quantity;

    public CreateItemDetails() {
    }

    public CreateItemDetails(String templateId, HashMap<String, Object> attributes, int quantity) {
        this.templateId = templateId;
        this.attributes = attributes;
        this.quantity = quantity;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
