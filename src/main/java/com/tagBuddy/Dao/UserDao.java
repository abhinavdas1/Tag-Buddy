package com.tagBuddy.Dao;

import com.tagBuddy.Entity.User;

/**
 * Created by abhinavdas on 1/20/18.
 */

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.*;

import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    static MongoClientURI uri;
    static MongoClient client;
    static MongoDatabase db;

    public void addUserData(User user)
    {
        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> users = db.getCollection("Users");
        MongoCollection<Document> tagUsers = db.getCollection("TagUsers");

        List<Document> seedData = new ArrayList<Document>();
        Map<String, Integer> tagCount = new HashMap<String, Integer>();

        for(int i = 0; i < user.getTags().size(); i++)
        {
            int count  = tagCount.getOrDefault(user.getTags().get(i), 0);
            tagCount.put(user.getTags().get(i), ++count);

            Document findQuery = new Document("tag", user.getTags().get(i));
            Document item = new Document("users", user.getUserId());
            Document updateQuery = new Document("$push", item);
            tagUsers.updateOne(findQuery, updateQuery);

        }

        Document record = new Document("id", user.getUserId());
        Document tags = new Document();

        for(Map.Entry<String, Integer> entry : tagCount.entrySet())
        {
            tags.append(entry.getKey(), entry.getValue());
        }


        seedData.add(record.append("tags" , tags)
        );

        users.insertMany(seedData);

        client.close();
    }

}
