package org.realityfn.fortnite.core.utils.repositories.fortnite;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.realityfn.common.utils.database.setup.MongoDBConnection;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.enums.CatalogRequirementType;
import org.realityfn.fortnite.core.enums.CurrencyType;
import org.realityfn.fortnite.core.models.fortnite.shop.CatalogEntry;
import org.realityfn.fortnite.core.models.fortnite.shop.ItemGrant;
import org.realityfn.fortnite.core.models.fortnite.shop.MetaInfo;
import org.realityfn.fortnite.core.models.fortnite.shop.gifts.GiftInfo;
import org.realityfn.fortnite.core.models.fortnite.shop.requirements.Requirement;
import org.realityfn.fortnite.core.models.fortnite.shop.currencies.Currency;

import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Projections.include;

@Singleton
public class ShopItemRepository {

    private static final MongoDatabase mongoDatabase = MongoDBConnection.getDatabase();

    private MongoCollection<Document> getCollection() {
        return mongoDatabase
                .getCollection(BuildProperties.APP + "_shop_items");
    }

    /**
     * Find a single item by offerId
     */
    public Optional<CatalogEntry> findByOfferId(String offerId) {
        if (offerId == null || offerId.isEmpty()) {
            return Optional.empty();
        }

        Document doc = getCollection()
                .find(eq("_id", offerId))
                .first();

        return Optional.ofNullable(doc).map(this::mapToCatalogEntry);
    }

    /**
     * Batch fetch multiple items by offerIds
     */
    public List<CatalogEntry> findByOfferIds(List<String> offerIds) {
        if (offerIds == null || offerIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Document> docs = getCollection()
                .find(in("_id", offerIds))
                .into(new ArrayList<>());

        return docs.stream()
                .map(this::mapToCatalogEntry)
                .collect(Collectors.toList());
    }

    /**
     * Check if an offer exists without loading full document
     */
    public boolean existsByOfferId(String offerId) {
        if (offerId == null || offerId.isEmpty()) {
            return false;
        }

        return getCollection()
                .find(eq("_id", offerId))
                .projection(include("_id"))
                .limit(1)
                .first() != null;
    }

    /**
     * Save or update a catalog entry
     */
    @SuppressWarnings("unused")
    public void upsert(CatalogEntry entry) {
        Document doc = catalogEntryToDocument(entry);

        getCollection().replaceOne(
                eq("_id", entry.getOfferId()),
                doc,
                new ReplaceOptions().upsert(true)
        );
    }

    /**
     * Delete an item by offerId
     */
    @SuppressWarnings("unused")
    public void deleteByOfferId(String offerId) {
        getCollection().deleteOne(eq("_id", offerId));
    }

    /**
     * Map MongoDB Document to CatalogEntry
     */
    @SuppressWarnings("unchecked")
    private CatalogEntry mapToCatalogEntry(Document doc) {
        CatalogEntry entry = new CatalogEntry();

        // Basic fields
        entry.setOfferId(doc.getString("_id"));
        entry.setDevName(doc.getString("devName"));
        entry.setOfferType(doc.getString("offerType"));
        entry.setTitle(doc.getString("title"));
        entry.setShortDescription(doc.getString("shortDescription"));
        entry.setDescription(doc.getString("description"));
        entry.setDisplayAssetPath(doc.getString("displayAssetPath"));
        entry.setMatchFilter(doc.getString("matchFilter"));
        entry.setCatalogGroup(doc.getString("catalogGroup"));

        // Numeric fields
        entry.setDailyLimit(doc.getInteger("dailyLimit", -1));
        entry.setWeeklyLimit(doc.getInteger("weeklyLimit", -1));
        entry.setMonthlyLimit(doc.getInteger("monthlyLimit", -1));
        entry.setSortPriority(doc.getInteger("sortPriority", 0));
        entry.setCatalogGroupPriority(doc.getInteger("catalogGroupPriority", 0));
        entry.setFilterWeight(doc.getInteger("filterWeight"));

        // Boolean fields
        entry.setRefundable(doc.getBoolean("refundable", false));

        // Lists
        entry.setFulfillmentIds(doc.getList("fulfillmentIds", String.class, new ArrayList<>()));
        entry.setCategories(doc.getList("categories", String.class, new ArrayList<>()));
        entry.setAppStoreId(doc.getList("appStoreId", String.class, new ArrayList<>()));

        // Complex objects - prices
        List<Document> pricesDocs = doc.getList("prices", Document.class, new ArrayList<>());
        List<Currency> prices = pricesDocs.stream()
                .map(this::mapToPrice)
                .collect(Collectors.toList());
        entry.setPrices(prices);

        // Complex objects - requirements
        List<Document> reqDocs = doc.getList("requirements", Document.class, new ArrayList<>());
        List<Requirement> requirements = reqDocs.stream()
                .map(this::mapToRequirement)
                .collect(Collectors.toList());
        entry.setRequirements(requirements);

        // Complex objects - itemGrants
        List<Document> grantDocs = doc.getList("itemGrants", Document.class, new ArrayList<>());
        List<ItemGrant> itemGrants = grantDocs.stream()
                .map(this::mapToItemGrant)
                .collect(Collectors.toList());
        entry.setItemGrants(itemGrants);

        // Gift info
        Document giftDoc = doc.get("giftInfo", Document.class);
        if (giftDoc != null) {
            entry.setGiftInfo(mapToGiftInfo(giftDoc));
        }

        // MetaInfo - array of key-value pairs
        List<Document> metaInfoDocs = doc.getList("metaInfo", Document.class, new ArrayList<>());
        List<MetaInfo> metaInfo = metaInfoDocs.stream()
                .map(this::mapToMetaInfo)
                .collect(Collectors.toList());
        entry.setMetaInfo(metaInfo);

        return entry;
    }

    private Currency mapToPrice(Document doc) {
        Currency price = new Currency();
        price.setCurrencyType(CurrencyType.valueOf(doc.getString("currencyType")));
        price.setCurrencySubType(doc.getString("currencySubType"));
        price.setRegularPrice(doc.getInteger("regularPrice", 0));
        price.setDynamicRegularPrice(doc.getInteger("dynamicRegularPrice", 0));
        price.setFinalPrice(doc.getInteger("finalPrice", 0));
        price.setBasePrice(doc.getInteger("basePrice", 0));
        price.setSaleExpiration(doc.getString("saleExpiration"));
        return price;
    }

    private Requirement mapToRequirement(Document doc) {
        Requirement req = new Requirement();
        req.setRequirementType(CatalogRequirementType.valueOf(doc.getString("requirementType")));
        req.setRequirementId(doc.getString("requirementId"));
        req.setMinQuantity(doc.getInteger("minQuantity", 1));
        return req;
    }

    private ItemGrant mapToItemGrant(Document doc) {
        ItemGrant grant = new ItemGrant();
        grant.setTemplateId(doc.getString("templateId"));
        grant.setQuantity(doc.getInteger("quantity", 1));
        return grant;
    }

    private GiftInfo mapToGiftInfo(Document doc) {
        GiftInfo giftInfo = new GiftInfo();
        giftInfo.setEnabled(doc.getBoolean("bIsEnabled", false));
        giftInfo.setForcedGiftBoxTemplateId(doc.getString("forcedGiftBoxTemplateId"));

        List<Document> reqDocs = doc.getList("purchaseRequirements", Document.class, new ArrayList<>());
        List<Requirement> requirements = reqDocs.stream()
                .map(this::mapToRequirement)
                .collect(Collectors.toList());
        giftInfo.setPurchaseRequirements(requirements);

        giftInfo.setGiftRecordIds(doc.getList("giftRecordIds", String.class, new ArrayList<>()));
        return giftInfo;
    }

    private MetaInfo mapToMetaInfo(Document doc) {
        MetaInfo meta = new MetaInfo();
        meta.setKey(doc.getString("key"));
        meta.setValue(doc.getString("value"));
        return meta;
    }

    /**
     * Convert CatalogEntry to MongoDB Document
     */
    private Document catalogEntryToDocument(CatalogEntry entry) {
        Document doc = new Document();

        doc.append("_id", entry.getOfferId());
        doc.append("devName", entry.getDevName());
        doc.append("offerType", entry.getOfferType());
        doc.append("title", entry.getTitle());
        doc.append("shortDescription", entry.getShortDescription());
        doc.append("description", entry.getDescription());
        doc.append("displayAssetPath", entry.getDisplayAssetPath());
        doc.append("matchFilter", entry.getMatchFilter());
        doc.append("catalogGroup", entry.getCatalogGroup());

        doc.append("dailyLimit", entry.getDailyLimit());
        doc.append("weeklyLimit", entry.getWeeklyLimit());
        doc.append("monthlyLimit", entry.getMonthlyLimit());
        doc.append("sortPriority", entry.getSortPriority());
        doc.append("catalogGroupPriority", entry.getCatalogGroupPriority());
        doc.append("filterWeight", entry.getFilterWeight());
        doc.append("refundable", entry.isRefundable());

        doc.append("fulfillmentIds", entry.getFulfillmentIds());
        doc.append("categories", entry.getCategories());
        doc.append("appStoreId", entry.getAppStoreId());

        // Convert complex objects
        if (entry.getPrices() != null) {
            List<Document> priceDocs = entry.getPrices().stream()
                    .map(this::priceToDocument)
                    .collect(Collectors.toList());
            doc.append("prices", priceDocs);
        }

        if (entry.getRequirements() != null) {
            List<Document> reqDocs = entry.getRequirements().stream()
                    .map(this::requirementToDocument)
                    .collect(Collectors.toList());
            doc.append("requirements", reqDocs);
        }

        if (entry.getItemGrants() != null) {
            List<Document> grantDocs = entry.getItemGrants().stream()
                    .map(this::itemGrantToDocument)
                    .collect(Collectors.toList());
            doc.append("itemGrants", grantDocs);
        }

        if (entry.getGiftInfo() != null) {
            doc.append("giftInfo", giftInfoToDocument(entry.getGiftInfo()));
        }

        doc.append("meta", entry.getMeta());

        if (entry.getMetaInfo() != null) {
            List<Document> metaDocs = entry.getMetaInfo().stream()
                    .map(this::metaInfoToDocument)
                    .collect(Collectors.toList());
            doc.append("metaInfo", metaDocs);
        }

        return doc;
    }

    private Document priceToDocument(Currency price) {
        return new Document()
                .append("currencyType", price.getCurrencyType())
                .append("currencySubType", price.getCurrencySubType())
                .append("regularPrice", price.getRegularPrice())
                .append("dynamicRegularPrice", price.getDynamicRegularPrice())
                .append("finalPrice", price.getFinalPrice())
                .append("basePrice", price.getBasePrice())
                .append("saleExpiration", price.getSaleExpiration());
    }

    private Document requirementToDocument(Requirement req) {
        return new Document()
                .append("requirementType", req.getRequirementType())
                .append("requiredId", req.getRequirementId())
                .append("minQuantity", req.getMinQuantity());
    }

    private Document itemGrantToDocument(ItemGrant grant) {
        return new Document()
                .append("templateId", grant.getTemplateId())
                .append("quantity", grant.getQuantity());
    }

    private Document giftInfoToDocument(GiftInfo giftInfo) {
        Document doc = new Document()
                .append("bIsEnabled", giftInfo.isEnabled())
                .append("forcedGiftBoxTemplateId", giftInfo.getForcedGiftBoxTemplateId())
                .append("giftRecordIds", giftInfo.getGiftRecordIds());

        if (giftInfo.getPurchaseRequirements() != null) {
            List<Document> reqDocs = giftInfo.getPurchaseRequirements().stream()
                    .map(this::requirementToDocument)
                    .collect(Collectors.toList());
            doc.append("purchaseRequirements", reqDocs);
        }

        return doc;
    }

    private Document metaInfoToDocument(MetaInfo meta) {
        return new Document()
                .append("key", meta.getKey())
                .append("value", meta.getValue());
    }
}