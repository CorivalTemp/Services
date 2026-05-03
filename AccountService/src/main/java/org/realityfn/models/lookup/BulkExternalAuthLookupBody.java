package org.realityfn.models.lookup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BulkExternalAuthLookupBody {
    @JsonProperty("authType")
    public String AuthType;

    @JsonProperty("displayNames")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> DisplayNames;

    @JsonProperty("ids")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> Ids;
}
