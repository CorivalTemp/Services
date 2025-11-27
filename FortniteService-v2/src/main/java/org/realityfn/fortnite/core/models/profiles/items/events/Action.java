package org.realityfn.fortnite.core.models.profiles.items.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;

import java.util.List;

public class Action {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean hasRun;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ProfileItem> itemsToGrant;
}
