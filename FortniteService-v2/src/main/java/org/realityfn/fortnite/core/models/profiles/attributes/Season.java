package org.realityfn.fortnite.core.models.profiles.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Season {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer numWins;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer seasonXp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean purchasedVIP;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer survivorPrestige;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer seasonLevel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer numLowBracket;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer bookLevel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer numRoyalRoyales;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer bookXp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer seasonNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer survivorTier;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer numHighBracket;
}
