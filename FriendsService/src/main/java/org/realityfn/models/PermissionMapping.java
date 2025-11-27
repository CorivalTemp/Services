package org.realityfn.models;

import org.realityfn.enums.Actions;

public class PermissionMapping {
    public String resource;
    public int action;

    public PermissionMapping() {
        this.resource = "";
        this.action = Actions.NONE;
    }

    public PermissionMapping(String resource, int action) {
        this.resource = resource;
        this.action = action;
    }
}