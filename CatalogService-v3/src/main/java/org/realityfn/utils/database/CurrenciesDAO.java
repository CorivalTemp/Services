package org.realityfn.utils.database;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.realityfn.common.errorhandling.exceptions.common.MongoExecutionTimeoutError;
import org.realityfn.models.DTO.CatalogCurrencyDTO;
import org.realityfn.models.mongo.CatalogCurrencyMongo;
import org.realityfn.common.utils.database.setup.BaseDAOImpl;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrenciesDAO extends BaseDAOImpl<CatalogCurrencyMongo> {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public CurrenciesDAO() {
        super("currencies", CatalogCurrencyMongo.class);
    }

    /**
     * Get all currencies
     */
    public List<CatalogCurrencyMongo> getAllFields() {
        try {
            return collection.find()
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), CatalogCurrencyMongo.class);
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
     * Get currency item by ID
     */
    public Optional<CatalogCurrencyMongo> getCurrencyByCode(String currencyCode) {
        try {
            Document doc = collection.find(
                    Filters.eq("code", currencyCode)).first();

            if (doc != null)
                return Optional.of(mapper.readValue(doc.toJson(), CatalogCurrencyMongo.class));

            return Optional.empty();
        } catch (Exception e) {
            throw new MongoExecutionTimeoutError();
        }
    }

    /**
     * Helper method to get all dlc item data from itemId list
     * @param currencyCodes List of item IDs to fetch
     * @return List of CatalogCurrencyMongo objects
     */
    public List<CatalogCurrencyMongo> getCurrenciesByCodes(List<String> currencyCodes) {
        if (currencyCodes == null || currencyCodes.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return collection.find(Filters.in("code", currencyCodes))
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), CatalogCurrencyMongo.class);
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
     * Get items per count
     * @param count Number of items to fetch
     * @return List of CatalogCurrencyMongo objects
     */
    public List<CatalogCurrencyDTO> getCurrenciesPerCount(int start, int count) {
        try {
            return collection.find()
                    .skip(start)
                    .limit(count)
                    .map(doc -> {
                        try {
                            CatalogCurrencyMongo entity = mapper.readValue(doc.toJson(), CatalogCurrencyMongo.class);
                            return toDTO(entity);
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

    public CatalogCurrencyDTO toDTO(CatalogCurrencyMongo entity) {
        return new CatalogCurrencyDTO(
                entity.getType(),
                entity.getCode(),
                entity.getSymbol(),
                entity.getDescription(),
                entity.getDecimals(),
                entity.getTruncLength(),
                entity.getPriceRanges()
        );
    }
}


