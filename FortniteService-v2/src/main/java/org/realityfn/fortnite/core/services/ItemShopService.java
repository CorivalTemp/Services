package org.realityfn.fortnite.core.services;

import com.mongodb.annotations.Sealed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import org.realityfn.common.exceptions.common.ServerErrorException;
import org.realityfn.fortnite.core.models.fortnite.shop.CatalogEntry;
import org.realityfn.fortnite.core.models.fortnite.shop.Storefront;
import org.realityfn.fortnite.core.models.fortnite.shop.StorefrontEntry;
import org.realityfn.fortnite.core.models.mongo.ShopMeta;
import org.realityfn.fortnite.core.utils.repositories.fortnite.ShopItemRepository;
import org.realityfn.fortnite.core.utils.repositories.fortnite.ShopMetaRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ItemShopService {


    @Inject
    private ShopMetaRepository shopMetaRepository;

    @Inject
    private ShopItemRepository shopItemRepository;

    /**
     * Get the complete item shop
     */
    public Storefront processItemShop() throws ServerErrorException {
        try {
            ShopMeta meta = shopMetaRepository.getCurrent();

            return buildStorefront(meta);
        } catch (Exception e) {
            throw new ServerErrorException(e);
        }
    }

    /**
     * Build complete storefront from metadata
     */
    private Storefront buildStorefront(ShopMeta meta) {
        Storefront storefront = new Storefront();
        storefront.setRefreshIntervalHrs(meta.getRefreshIntervalHrs());
        storefront.setDailyPurchaseHrs(meta.getDailyPurchaseHrs());
        storefront.setExpiration(meta.getExpiration());

        List<StorefrontEntry> storefronts = new ArrayList<>();

        // Maintain order from meta
        for (Map.Entry<String, List<String>> entry : meta.getStorefronts().entrySet()) {
            String storefrontName = entry.getKey();
            List<String> offerIds = entry.getValue();

            // Batch fetch all items for this storefront
            List<CatalogEntry> items = shopItemRepository.findByOfferIds(offerIds);

            // Maintain the order from meta (offerIds are pre-sorted)
            Map<String, CatalogEntry> itemMap = items.stream()
                    .collect(Collectors.toMap(CatalogEntry::getOfferId, Function.identity()));

            List<CatalogEntry> sortedItems = offerIds.stream()
                    .map(itemMap::get)
                    .filter(Objects::nonNull) // Skip if item not found
                    .collect(Collectors.toList());

            StorefrontEntry storefrontEntry = new StorefrontEntry();
            storefrontEntry.setName(storefrontName);
            storefrontEntry.setCatalogEntries(sortedItems);

            storefronts.add(storefrontEntry);
        }

        storefront.setStorefronts(storefronts);
        return storefront;
    }

    /**
     * Get a specific offer by ID - O(1) lookup
     */
    public CatalogEntry getOfferById(String offerId) throws ServerErrorException {
        if (offerId == null || offerId.isEmpty()) {
            return null;
        }

        try {
            return shopItemRepository.findByOfferId(offerId).orElse(null);
        } catch (Exception e) {
            throw new ServerErrorException(e);
        }
    }

    /**
     * Check if an offer is available - O(1) existence check
     */
    public boolean isOfferAvailable(String offerId) throws ServerErrorException {
        if (offerId == null || offerId.isEmpty()) {
            return false;
        }

        try {
            return shopItemRepository.existsByOfferId(offerId);
        } catch (Exception e) {
            throw new ServerErrorException(e);
        }
    }

    /**
     * Get offers by storefront name
     */
    public List<CatalogEntry> getOffersByStorefront(String storefrontName) throws ServerErrorException {
        try {
            ShopMeta meta = shopMetaRepository.getCurrent();
            List<String> offerIds = meta.getStorefronts().get(storefrontName);

            if (offerIds == null || offerIds.isEmpty()) {
                return Collections.emptyList();
            }

            return shopItemRepository.findByOfferIds(offerIds);
        } catch (Exception e) {
            throw new ServerErrorException(e);
        }
    }
}