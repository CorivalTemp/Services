package org.realityfn.fortnite.core;

import org.glassfish.jersey.server.ServerProperties;
import org.realityfn.common.BaseApplication;
import org.realityfn.fortnite.core.config.FortniteConfig;
import org.realityfn.fortnite.core.config.LightswitchConfig;
import org.realityfn.fortnite.core.config.SpringBridge;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class FortniteApplication extends BaseApplication {
    public FortniteApplication() {
    }

    protected abstract void registerControllers();

    @Override
    protected void registerResources() {
        register(new SpringBridge(new AnnotationConfigApplicationContext(FortniteConfig.class, LightswitchConfig.class)));
        packages("org.realityfn.fortnite.core.annotations",
                "org.realityfn.fortnite.core.enums",
                "org.realityfn.fortnite.core.errorhandling",
                "org.realityfn.fortnite.core.filters",
                "org.realityfn.fortnite.core.managers",
                "org.realityfn.fortnite.core.models",
                "org.realityfn.fortnite.core.registries",
                "org.realityfn.fortnite.core.utils",
                "org.realityfn.fortnite.core.managers"
        );
        registerControllers();
    }
}
