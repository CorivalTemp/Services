package org.realityfn.common.utils.database.setup;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoDBConnection {
    private static final MongoClient mongoClient;
    private static final MongoDatabase mongoDatabase;
    private static final Properties props = new Properties();
    public static final String CONNECTION_URL;
    public static final String DATABASE_COLLECTION;

    static {
        try (InputStream is = MongoDBConnection.class.getResourceAsStream("/common.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load common.properties", e);
        }

        CONNECTION_URL = props.getProperty("database.connection.url");
        DATABASE_COLLECTION = props.getProperty("database.connection.collection");

        CodecRegistry pojoCodecRegistry = fromProviders(
                PojoCodecProvider.builder()
                        .automatic(true)
                        .build()
        );

        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_URL))
                .codecRegistry(codecRegistry)
                .build();

        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase(DATABASE_COLLECTION);
    }

    public static MongoDatabase getDatabase() {
        return mongoDatabase;
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}