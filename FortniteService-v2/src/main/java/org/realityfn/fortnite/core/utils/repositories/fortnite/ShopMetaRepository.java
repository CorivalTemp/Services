package org.realityfn.fortnite.core.utils.repositories.fortnite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.realityfn.common.utils.database.setup.MongoDBConnection;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.models.mongo.ShopMeta;

@Singleton
public class ShopMetaRepository {

    private static final MongoDatabase mongoDatabase = MongoDBConnection.getDatabase();

    private MongoCollection<Document> getCollection() {
        return mongoDatabase
                .getCollection("shop_meta");
    }

    public ShopMeta getCurrent() {
        Document doc = getCollection().find(Filters.in("_id", BuildProperties.APP)).first();
        return mapToShopMeta(doc);
    }

    private ShopMeta mapToShopMeta(Document doc) {
        try {
            String json = doc.toJson();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ShopMeta.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map shop meta", e);
        }
    }
}