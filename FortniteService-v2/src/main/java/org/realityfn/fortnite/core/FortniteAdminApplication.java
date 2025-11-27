package org.realityfn.fortnite.core;

public class FortniteAdminApplication extends FortniteApplication {
    public FortniteAdminApplication() {}

    @Override
    protected void registerControllers() {
        packages("org.realityfn.fortnite.core.controllers");
    }
}