package org.realityfn.fortnite.core.models.fortnite.shop.requirements;

import org.realityfn.fortnite.core.enums.CatalogRequirementType;
import org.springframework.data.mongodb.core.mapping.Field;

public class Requirement {
    @Field("requirementType")
    private CatalogRequirementType requirementType;

    @Field("requirementId")
    private String requirementId;

    @Field("minQuantity")
    private int minQuantity;

    // Default constructor for Spring Data MongoDB
    public Requirement() {
    }

    // Constructor for manual object creation
    public Requirement(CatalogRequirementType requirementType, String requirementId, int minQuantity) {
        this.requirementType = requirementType;
        this.requirementId = requirementId;
        this.minQuantity = minQuantity;
    }

    // Getter and Setter methods
    public CatalogRequirementType getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(CatalogRequirementType requirementType) {
        this.requirementType = requirementType;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }
}