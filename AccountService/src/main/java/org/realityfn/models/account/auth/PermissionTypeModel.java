package org.realityfn.models.account.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.models.authorization.PermissionMapping;

import java.util.ArrayList;
import java.util.List;

public class PermissionTypeModel {
    @JsonProperty("client")
    public List<PermissionMapping> client = new ArrayList<>();

    @JsonProperty("account")
    public List<PermissionMapping> account = new ArrayList<>();
}
