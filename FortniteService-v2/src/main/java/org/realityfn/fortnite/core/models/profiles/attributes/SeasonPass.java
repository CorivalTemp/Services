package org.realityfn.fortnite.core.models.profiles.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SeasonPass {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean gainedTierOneRewards;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean purchased;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer level;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean purchasedAtLeastOnce;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer premiumMtxGained;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String seasonTemplateId;
}
