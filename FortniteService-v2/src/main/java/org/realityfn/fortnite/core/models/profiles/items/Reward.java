package org.realityfn.fortnite.core.models.profiles.items;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Reward {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String must_have_token_id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ProfileItem> rewards;
}
