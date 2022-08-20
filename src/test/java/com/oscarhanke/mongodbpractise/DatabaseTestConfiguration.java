package com.oscarhanke.mongodbpractise;

import com.oscarhanke.mongodbpractise.config.GenericCascadeListener;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@TestConfiguration
public class DatabaseTestConfiguration {
    @Bean
    GenericCascadeListener genericCascadeListener(MongoTemplate mongoTemplate){
        return new GenericCascadeListener(mongoTemplate);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(){
        return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/db-tests -u rootuser -p rootpass");
    }
    @Bean
    MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory){
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
