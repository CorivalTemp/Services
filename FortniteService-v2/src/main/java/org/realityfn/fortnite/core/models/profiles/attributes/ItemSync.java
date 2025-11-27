package org.realityfn.fortnite.core.models.profiles.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ItemSync {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String lastCheckSum;
}
