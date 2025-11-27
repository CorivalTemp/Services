package org.realityfn.fortnite.core;

public class FortnitePublicApplication extends FortniteApplication {
    public FortnitePublicApplication() {}

    @Override
    protected void registerControllers() {
        packages("org.realityfn.fortnite.core.controllers.apipublic");
    }
}