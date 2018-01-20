package com.tagBuddy.Dao;

//import com.sun.tools.javac.util.*;
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
import java.util.List;

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
        }

        for(Map.Entry<String, Integer> entry : tagCount.entrySet())
        {
            ArrayList<String> in = new ArrayList<String>();
            in.add(entry.getKey());
            Document query = new Document("tag", new Document("$exists", true).append("$in" , in));

            MongoCursor<Document> cursor = tagUsers.find(query).iterator();

            try {
                if(!cursor.hasNext()) {
                    tagUsers.insertOne(new Document("tag", entry.getKey()).append("users", new ArrayList<String>()));
                }
            } finally {

            }

            Document findQuery = new Document("tag", entry.getKey());
            Document item = new Document("users", user.getUserId());
            Document updateQuery = new Document("$push", item);
            tagUsers.updateOne(findQuery, updateQuery);
        }

        Document record = new Document("id", user.getUserId());
        List<Document> tags = new ArrayList<Document>();

        for(Map.Entry<String, Integer> entry : tagCount.entrySet())
        {
            tags.add(new Document("name", entry.getKey()).append("val", entry.getValue()));
        }


        seedData.add(record.append("tags" , tags)
        );

        users.insertMany(seedData);

        client.close();
    }

    public Map<String,Integer> getTagCount(String id) {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> users = db.getCollection("Users");
        Document query = new Document("id", id);

        MongoCursor<Document> cursor = users.find(query).iterator();
        Map<String, Integer> result = new HashMap<String, Integer>();

        try {
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                List<Document> tags = (List<Document>) doc.get("tags");

                for(Document d : tags)
                {
                    result.put((String)d.get("name"), (Integer)d.get("val"));
                }
            }
        } finally {

        }

        client.close();

        return result;

    }

    public Map<String,java.util.List<String>> getUserTags() {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> tagUsers = db.getCollection("TagUsers");
        Document query = new Document();

        MongoCursor<Document> cursor = tagUsers.find(query).iterator();
        Map<String, List<String>> result = new HashMap<String, List<String>>();

        try {
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                String name = (String)doc.get("tag");
                List<String> users = (List<String>) doc.get("users");

                result.put(name, users);
            }
        } finally {

        }

        return result;
    }
}
