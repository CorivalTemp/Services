package org.realityfn;

import jakarta.ws.rs.ApplicationPath;
import org.realityfn.common.BaseApplication;

@ApplicationPath("/catalog")
public class CatalogAdminApplication extends BaseApplication {
    public CatalogAdminApplication() {}

    @Override
    protected void registerResources() {
        packages("org.realityfn");
    }
}