package org.realityfn.fortnite.core.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableMongoRepositories(
        basePackages = "org.realityfn.fortnite.core.utils.repositories.lightswitch",
        mongoTemplateRef = "lightswitchMongoTemplate"
)
public class LightswitchConfig {
    private static final MongoClient mongoClient;
    private static final MongoDatabase mongoDatabase;
    private static final Properties props = new Properties();
    public static final String CONNECTION_URL;
    public static final String DATABASE_COLLECTION;

    static {
        try (InputStream is = LightswitchConfig.class.getResourceAsStream("/common.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load common.properties", e);
        }

        CONNECTION_URL = props.getProperty("database.connection.url");
        DATABASE_COLLECTION = props.getProperty("lightswitch.connection.collection");
        mongoClient = MongoClients.create(CONNECTION_URL);
        mongoDatabase = mongoClient.getDatabase(DATABASE_COLLECTION);
    }

    @Bean(name = "lightswitchMongoClient")
    public MongoClient lightswitchMongoClient() {
        return mongoClient;
    }

    @Bean(name = "lightswitchMongoTemplate")
    public MongoTemplate lightswitchMongoTemplate() {
        return new MongoTemplate(lightswitchMongoClient(), DATABASE_COLLECTION);
    }

    public static MongoDatabase getDatabase() {
        return mongoDatabase;
    }
}