package org.realityfn.utils.database;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.realityfn.common.errorhandling.exceptions.common.MongoExecutionTimeoutError;
import org.realityfn.models.DTO.CatalogItemDTO;
import org.realityfn.models.mongo.CatalogItemMongo;
import org.realityfn.common.utils.database.setup.BaseDAOImpl;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemsDAO extends BaseDAOImpl<CatalogItemMongo> {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public ItemsDAO() {
        super("items", CatalogItemMongo.class);
    }

    /**
     * Get all items
     */
    public List<CatalogItemMongo> getAllFields() {
        try {
            return collection.find()
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), CatalogItemMongo.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new MongoExecutionTimeoutError();
        }
    }

    /**
     * Get item with itemId
     */
    public Optional<CatalogItemMongo> getItemById(String itemId) {
        try {
            Document doc = collection.find(
                    Filters.eq("_id", itemId)).first();

            if (doc != null)
                return Optional.of(mapper.readValue(doc.toJson(), CatalogItemMongo.class));

            return Optional.empty();
        } catch (Exception e) {
            throw new MongoExecutionTimeoutError();
        }
    }
    /**
     * Get all items under a specific namespace
     */
    public List<CatalogItemMongo> getAllItemsUnderNamespace(String namespace) {
        try {
            return collection.find(
                    Filters.eq("namespace", namespace))
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), CatalogItemMongo.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new MongoExecutionTimeoutError();
        }
    }

    /**
     * Helper method to get all dlc item data from itemId list
     * @param itemIds List of item IDs to fetch
     * @return List of CatalogItemMongo objects
     */
    public List<CatalogItemDTO> getItemsByIds(List<String> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return collection.find(Filters.in("_id", itemIds))
                    .map(doc -> {
                        try {
                            CatalogItemMongo mongo = mapper.readValue(doc.toJson(), CatalogItemMongo.class);
                            return toDTO(mongo);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MongoExecutionTimeoutError();
        }
    }

    /**
     * Get items per count
     * @param count Number of items to fetch
     * @return List of CatalogItemMongo objects
     */
    public List<CatalogItemMongo> getItemsPerCount(int count) {
        try {
            return collection.find()
                    .limit(count)
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), CatalogItemMongo.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new MongoExecutionTimeoutError();
        }
    }

    public CatalogItemDTO toDTO(CatalogItemMongo mongo) {
        return new CatalogItemDTO(
                mongo.getId(),
                mongo.getTitle(),
                mongo.getDescription(),
                mongo.getKeyImages(),
                mongo.getCategories(),
                mongo.getNamespace(),
                mongo.getStatus(),
                mongo.getCreationDate(),
                mongo.getLastModifiedDate(),
                mongo.getCustomAttributes(),
                mongo.getEntitlementName(),
                mongo.getEntitlementType(),
                mongo.getItemType(),
                mongo.getDlcItemIds(),
                mongo.getReleaseInfo(),
                mongo.getDeveloper(),
                mongo.getDeveloperId(),
                mongo.getUseCount(),
                mongo.getEulaIds(),
                mongo.getEndOfSupport(),
                mongo.getAgeGatings(),
                mongo.getApplicationId(),
                mongo.getUnsearchable()
        );
    }
}


