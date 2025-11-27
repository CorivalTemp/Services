package org.realityfn.fortnite.core.models.profiles.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemParam {
    public String seasonTemplateId;
    public String cmdPage;
    public boolean seasonOwned;

    @JsonProperty("SubheaderAssetString")
    public String subheaderAssetString;
}
