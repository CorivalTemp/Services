package org.realityfn.fortnite.core.config;

import com.mongodb.client.MongoClient;
import org.realityfn.common.utils.database.setup.MongoDBConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.realityfn.fortnite.core.utils.repositories.fortnite")
public class FortniteConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoDBConnection.getMongoClient();
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), MongoDBConnection.DATABASE_COLLECTION);
    }
}