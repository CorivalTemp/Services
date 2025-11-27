package org.realityfn.utils.database.setup;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    static {
        mongoClient = MongoClients.create("mongodb://");
        mongoDatabase = mongoClient.getDatabase("FriendsService");
    }

    public static MongoDatabase getDatabase() {
        return mongoDatabase;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
