package org.realityfn.fortnite.core.models.fortnite;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.fortnite.core.enums.VersionCheck;


public class VersionCheckModel {
    VersionCheck type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String appRedirect;

    public VersionCheckModel(VersionCheck type) {
        this.type = type;
    }

    public VersionCheckModel(String appRedirect) {
        this.type = VersionCheck.APP_REDIRECT;
        this.appRedirect = appRedirect;
    }

    // Josh forgot to add getters and setters :sob: :pray:
    public VersionCheck getType() {
        return type;
    }

    public String getAppRedirect() {
        return appRedirect;
    }
}