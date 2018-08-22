package com.deep.baiduspider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: george
 * @Date: 2018/6/13-11-20
 * @Description:
 */
@Configuration
public class MongoConfig {
    @Value("${mongodb.host}")
    private String host;
    @Value("${mongodb.port}")
    private Integer port;

    @Value("${mongodb.maxConnect}")
    private String maxConnect;
    @Value("${mongodb.maxWaitThread}")
    private String maxWaitThread;
    @Value("${mongodb.maxTimeOut}")
    private String maxTimeOut;
    @Value("${mongodb.maxWaitTime}")
    private String maxWaitTime;

    @Value("${mongodb.username}")
    private String username;
    @Value("${mongodb.password}")
    private String password;
    @Value("${mongodb.database}")
    private String database;
    @Value("${mongodb.collection")
    private String collection;

    @Bean
    public MongoClient mongoClient(){
        MongoClient mongoClient = null;

        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(Integer.valueOf(maxConnect));
        build.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(maxWaitThread));
        build.connectTimeout(Integer.valueOf(maxTimeOut) * 1000);
        build.maxWaitTime(Integer.valueOf(maxWaitTime) * 1000);
        MongoClientOptions options = build.build();

        try{
            ServerAddress serverAddress = new ServerAddress(host,port);
            MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username,database,password.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(mongoCredential);
            mongoClient = new MongoClient(serverAddress,credentials,options);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mongoClient;
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient){
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        return mongoDatabase;
    }

    @Bean
    public MongoCollection mongoCollection(MongoDatabase mongoDatabase){
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        return mongoCollection;
    }

}
