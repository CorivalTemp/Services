package org.realityfn;

import jakarta.ws.rs.ApplicationPath;
import org.realityfn.common.BaseApplication;

@ApplicationPath("/catalog")
public class CatalogPublicApplication extends BaseApplication {
    public CatalogPublicApplication() {}

    @Override
    protected void registerResources() {
        packages("org.realityfn.annotations",
                "org.realityfn.controllers.apipublic",
                "org.realityfn.common.controllers",
                "org.realityfn.enums",
                "org.realityfn.errorhandling",
                "org.realityfn.filters",
                "org.realityfn.managers",
                "org.realityfn.models",
                "org.realityfn.registries",
                "org.realityfn.utils");
    }
}