package org.realityfn.fortnite.core.models.profiles.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class LoadoutPresets {
    @JsonProperty("CosmeticLoadout:LoadoutSchema_Emotes")
    public Map<String, String> emotes;

    @JsonProperty("CosmeticLoadout:LoadoutSchema_Sparks")
    public Map<String, String> sparks;

    @JsonProperty("CosmeticLoadout:LoadoutSchema_Platform")
    public Map<String, String> platform;

    @JsonProperty("CosmeticLoadout:LoadoutSchema_Wraps")
    public Map<String, String> wraps;

    @JsonProperty("CosmeticLoadout:LoadoutSchema_Jam")
    public Map<String, String> jam;

    @JsonProperty("CosmeticLoadout:LoadoutSchema_Vehicle_SUV")
    public Map<String, String> suv;

    @JsonProperty("CosmeticLoadout:LoadoutSchema_Vehicle")
    public Map<String, String> vehicle;

    @JsonProperty("CosmeticLoadout:LoadoutSchema_Character")
    public Map<String, String> character;
}
