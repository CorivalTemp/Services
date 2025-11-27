package org.realityfn.fortnite.core.game.commands.mtx;

import net.sf.oval.constraint.NotNull;
import org.realityfn.common.exceptions.common.ServerErrorException;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.CatalogRequirementType;
import org.realityfn.fortnite.core.enums.CurrencyType;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.exceptions.modules.gameplayutils.NotEnoughMtxException;
import org.realityfn.fortnite.core.exceptions.modules.gamesubcatalog.CatalogOutOfDateException;
import org.realityfn.fortnite.core.exceptions.modules.gamesubcatalog.InvalidParameterException;
import org.realityfn.fortnite.core.exceptions.modules.gamesubcatalog.PurchaseNotAllowedException;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.fortnite.shop.CatalogEntry;
import org.realityfn.fortnite.core.models.fortnite.shop.ItemGrant;
import org.realityfn.fortnite.core.models.fortnite.shop.currencies.Currency;
import org.realityfn.fortnite.core.models.fortnite.shop.requirements.Requirement;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.ProfileChangeRequest;
import org.realityfn.fortnite.core.models.profiles.attributes.MtxDetail;
import org.realityfn.fortnite.core.models.profiles.attributes.Purchase;
import org.realityfn.fortnite.core.models.profiles.attributes.PurchaseHistory;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;
import org.realityfn.fortnite.core.models.profiles.items.Template;
import org.realityfn.fortnite.core.models.profiles.notifications.LootItem;
import org.realityfn.fortnite.core.models.profiles.notifications.LootResult;
import org.realityfn.fortnite.core.models.profiles.notifications.Notification;
import org.realityfn.fortnite.core.services.ItemShopService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

// Bundles are not supported.
@ProfileOperation(operation = "PurchaseCatalogEntry", profiles = "common_core", routes = {ProfileRoute.CLIENT, ProfileRoute.DEDICATED_SERVER})
public class FortPurchaseCatalogEntry extends ProfileCommand {

    @NotNull
    String offerId;

    @NotNull
    int purchaseQuantity;

    @NotNull
    CurrencyType currency;

    @NotNull
    String currencySubType;

    @NotNull
    double expectedTotalPrice;

    @NotNull
    String gameContext;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile commonCoreProfile = operationContext.getPrimaryProfile();
        Profile athenaProfile = operationContext.getProfile("athena");

        ItemShopService itemShopService = ProfileManager.getInstance().getItemShopService();
        CatalogEntry catalogEntry = itemShopService.getOfferById(offerId);

        if (catalogEntry == null) {
            throw new CatalogOutOfDateException(offerId);
        }

        if (catalogEntry.getPrices().get(0).getCurrencyType() != CurrencyType.MtxCurrency) {
            throw new InvalidParameterException();
        }

        List<Currency> priceInfo = catalogEntry.getPrices();
        if (!Objects.equals(priceInfo.get(0).getCurrencyType(), currency) || !Objects.equals(priceInfo.get(0).getCurrencySubType(), currencySubType)) throw new ServerErrorException();

        if (catalogEntry.getDailyLimit() != -1 &&  purchaseQuantity > catalogEntry.getDailyLimit()) throw new PurchaseNotAllowedException("Maximum amount of purchases reached.");

        // Check for ownership in profile
        for (Requirement itemRequirement : catalogEntry.getRequirements()) {
            if (Objects.equals(itemRequirement.getRequirementType(), CatalogRequirementType.DenyOnItemOwnership)) {
                ProfileItem ownedItem = athenaProfile.findItemByTemplateId(itemRequirement.getRequirementId());
                if (ownedItem == null || ownedItem.getQuantity() < itemRequirement.getMinQuantity()) break;
                throw new PurchaseNotAllowedException("All items in this bundle are already owned");
            }
        }


        int totalOwnedMtx = calculateTotalMtx(commonCoreProfile);
        if (totalOwnedMtx < priceInfo.get(0).getFinalPrice()) throw new NotEnoughMtxException(catalogEntry.getDevName(), priceInfo.get(0).getFinalPrice(), totalOwnedMtx);
        purchaseWithMtx(commonCoreProfile, priceInfo.get(0).getFinalPrice());

        List<ItemGrant> itemsGranted = catalogEntry.getItemGrants();
        LootResult lootResult = new LootResult();

        // Here we add the items one by one from itemGrant
        lootResult.items = new ArrayList<>();
        List<LootItem> lootItems = new ArrayList<>(List.of());
        for (ItemGrant itemGranted : itemsGranted) {
            // Add the item to profile then show in ItemGrant
            String createdAt = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();

            HashMap<String, Object> attributesMap = new HashMap<>();
            attributesMap.put("created_at", createdAt);
            Template itemTemplate = Objects.requireNonNull(TemplateManager.getTemplate(itemGranted.getTemplateId()));
            ProfileChangeRequest.AddItemRequest addedItem =  athenaProfile.addItem(itemTemplate, attributesMap, itemGranted.getQuantity());

            // Create the loot item info for the item
            LootItem item = new LootItem(
                    addedItem.getItemId(),
                    itemTemplate.getTemplateProfile(), // Incase a purchase grants something not in athena profile.
                    itemGranted.getTemplateId(),
                    itemGranted.getQuantity()
            );
            lootItems.add(item);
            lootResult.items.add(item);
        }

        operationContext.addNotifications(Notification.catalogPurchase(lootResult, true));

        PurchaseHistory purchaseHistory = commonCoreProfile.getStat("mtx_purchase_history");
        if (purchaseHistory == null) {
            purchaseHistory = new PurchaseHistory(
                    0,
                    new ArrayList<>(List.of(new Purchase())),
                    3
            );
        }
        List<Purchase> purchases = purchaseHistory.getPurchases();


        List<MtxDetail> mtxDetails = List.of();
        Purchase purchaseInfo = Purchase.builder()
                .purchaseId(UUID.randomUUID().toString())
                .offerId(offerId)
                .purchaseDate(String.valueOf(Instant.now()))
                .totalMtxPaid(priceInfo.get(0).getFinalPrice())
                .gameContext(gameContext)
                .freeRefundEligible(false)
                .lootResult(lootItems)
                .metadata(new HashMap<>()) // TODO: used for SAC
                .mtxDetails(mtxDetails)
                .refundDate(String.valueOf(Instant.now()))
                .fulfillments(new ArrayList<>())
                .build();

        purchases.add(purchaseInfo);

        purchaseHistory.setPurchases(purchases);
        commonCoreProfile.changeStat("mtx_purchase_history", purchaseHistory);
    }

    private int calculateTotalMtx(Profile profile) {

        if (profile == null || profile.getItems() == null) return 0;

        String[] mtxTemplateIds = {
                "Currency:MtxPurchased",
                "Currency:MtxPurchaseBonus",
                "Currency:MtxGiveaway",
                "Currency:MtxComplimentary"
        };

        int totalMtx = 0;

        for (String templateId : mtxTemplateIds) {
            ProfileItem item = profile.getItems().values().stream()
                    .filter(i -> i.getTemplateId() != null && i.getTemplateId().equals(templateId))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                totalMtx += item.getQuantity();
            }
        }

        ProfileItem debtItem = profile.getItems().values().stream()
                .filter(i -> i.getTemplateId() != null && i.getTemplateId().equals("Currency:MtxDebt"))
                .findFirst()
                .orElse(null);

        if (debtItem != null) {
            totalMtx -= debtItem.getQuantity();
        }

        return totalMtx;
    }

    private void purchaseWithMtx(Profile profile, int price) {
        if (profile == null || profile.getItems() == null) return;

        String[] mtxTemplateIds = {
                "Currency:MtxPurchased",
                "Currency:MtxPurchaseBonus",
                "Currency:MtxGiveaway",
                "Currency:MtxComplimentary"
        };

        PriorityQueue<ProfileItem> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(ProfileItem::getQuantity)
        );

        for (ProfileItem item : profile.getItems().values()) {
            if (item.getTemplateId() != null) {
                for (String templateId : mtxTemplateIds) {
                    if (item.getTemplateId().equals(templateId)) {
                        minHeap.offer(item);
                        break;
                    }
                }
            }
        }

        ProfileItem debtItem = profile.getItems().values().stream()
                .filter(i -> i.getTemplateId() != null && i.getTemplateId().equals("Currency:MtxDebt"))
                .findFirst()
                .orElse(null);

        if (debtItem != null) {
            price += debtItem.getQuantity();
        }

        int remainingPrice = price;

        // Process items from smallest to largest
        while (!minHeap.isEmpty() && remainingPrice > 0) {
            ProfileItem item = minHeap.poll();
            int quantity = item.getQuantity();

            if (quantity >= remainingPrice) {
                item.setQuantity(quantity - remainingPrice);
                remainingPrice = 0;
            } else {
                remainingPrice -= quantity;
                profile.removeItem(item.getTemplateId());
            }
        }
    }
}

