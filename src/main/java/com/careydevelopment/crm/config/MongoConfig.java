package com.careydevelopment.crm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.careydevelopment.crm.util.PropertiesUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


@Configuration
@EnableCaching
@EnableMongoRepositories(basePackages = {"com.careydevelopment.crm.repository"})
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongo.db.name}") 
    private String crmDb;
    
    @Value("${crm.properties.file.location}")
    private String crmPropertiesFile;
    
    @Override
    protected String getDatabaseName() {
        return crmDb;
    }
  
    
    @Override
    @Bean
    public MongoClient mongoClient() {
        PropertiesUtil propertiesUtil = new PropertiesUtil(crmPropertiesFile);
        String connectionString = propertiesUtil.getProperty("mongodb.carey-crm.connection");
        String fullConnectionString = connectionString + "/" + crmDb;
        
        MongoClient client = MongoClients.create(fullConnectionString);
        return client;
    }
}
