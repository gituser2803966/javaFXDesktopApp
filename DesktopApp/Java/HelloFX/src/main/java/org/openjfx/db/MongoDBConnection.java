package org.openjfx.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBConnection {
//    final private static String url = "mongodb+srv://vantan:4Xqyv6S5vpuqlaaW@cluster0.zujpu.mongodb.net/BusDataManager?retryWrites=true&w=majority";
    static String connection_url = "mongodb+srv://vantan:4Xqyv6S5vpuqlaaW@cluster0.zujpu.mongodb.net/BusDataManager?retryWrites=true&w=majority";

    public static MongoDatabase GetDatabase() {

        String dbName = "BusData";
        MongoDatabase mongoDatabase;

        ConnectionString connectionString = new ConnectionString(connection_url);

        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());

        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();


        MongoClient mongoClient = MongoClients.create(clientSettings);

        return mongoDatabase  =  mongoClient.getDatabase(dbName);

    }

}
