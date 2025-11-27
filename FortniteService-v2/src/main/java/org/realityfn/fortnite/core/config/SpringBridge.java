package org.realityfn.fortnite.core.config;

import jakarta.inject.Singleton;
import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.services.FortniteService;
import org.realityfn.fortnite.core.services.ItemShopService;
import org.realityfn.fortnite.core.utils.repositories.fortnite.*;
import org.realityfn.fortnite.core.utils.repositories.lightswitch.LightswitchRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.springframework.context.ApplicationContext;

public class SpringBridge extends AbstractBinder {
    private final ApplicationContext springContext;

    public SpringBridge(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    protected void configure() {
        // Springboot repos
        bind(springContext.getBean(SystemCloudstorageRepository.class))
                .to(SystemCloudstorageRepository.class);
        bind(springContext.getBean(UserCloudstorageRepository.class))
                .to(UserCloudstorageRepository.class);
        bind(springContext.getBean(LightswitchRepository.class))
                .to(LightswitchRepository.class);
        bind(springContext.getBean(KeychainRepository.class))
                .to(KeychainRepository.class);


        // Jersey services
        bind(FortniteService.class)
                .to(FortniteService.class);

        bind(ProfileManager.class)
                .to(ProfileManager.class);

        bind(ShopMetaRepository.class)
                .to(ShopMetaRepository.class)
                .in(Singleton.class);

        bind(ShopItemRepository.class)
                .to(ShopItemRepository.class)
                .in(Singleton.class);

        bind(ItemShopService.class)
                .to(ItemShopService.class)
                .in(Singleton.class);

        bind(GameSessionRepository.class)
                .to(GameSessionRepository.class)
                .in(Singleton.class);

        bind(MatchmakingConfigRepository.class)
                .to(MatchmakingConfigRepository.class)
                .in(Singleton.class);

    }
}