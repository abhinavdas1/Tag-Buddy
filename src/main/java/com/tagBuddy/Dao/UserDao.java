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
        int totalTags = user.getTags().size();

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
            tags.add(new Document("name", entry.getKey()).append("val", (float) entry.getValue() / totalTags));
        }


        seedData.add(record.append("tags" , tags).append("name", user.getName()).append("picture", user.getPicture()));

        users.insertMany(seedData);

        client.close();
    }

    public Map<String,Double> getTagCount(String id) {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> users = db.getCollection("Users");
        Document query = new Document("id", id);

        MongoCursor<Document> cursor = users.find(query).iterator();
        Map<String, Double> result = new HashMap<String, Double>();

        try {
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                List<Document> tags = (List<Document>) doc.get("tags");

                for(Document d : tags)
                {
                    result.put((String)d.get("name"), (Double)d.get("val"));
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


    public boolean exists(String userId) {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> users = db.getCollection("Users");
        Document query = new Document("id", userId);


        MongoCursor<Document> cursor = users.find(query).iterator();

        try {
            if(cursor.hasNext()) {
                return true;
            }
        } finally {

        }

        client.close();

        return false;
    }

    public void removeUser(String userId) {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> users = db.getCollection("Users");
        Document query = new Document("id", userId);

        users.deleteOne(query);

        client.close();

    }

    public void removeUserInstances(String userId) {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> tagUsers = db.getCollection("TagUsers");

        Document query = new Document("users", userId);

        tagUsers.updateMany(query, new Document("$pull", new Document("users", userId)));

        client.close();

    }


    public Map<String,String> getNames() {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> users = db.getCollection("Users");
        Map<String,String> result = new HashMap<String, String>();

        MongoCursor<Document> cursor = users.find().iterator();

        try {
            while(cursor.hasNext()) {
                Document current = cursor.next();
                result.put((String)current.get("id"), (String)current.get("name"));
            }
        } finally {

        }

        client.close();

        return result;

    }

    public Map<String,String> getPictures() {

        uri  = new MongoClientURI("mongodb://abhinavdas:swamphacks@ds263837.mlab.com:63837/swamphack");
        client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> users = db.getCollection("Users");
        Map<String,String> result = new HashMap<String, String>();

        MongoCursor<Document> cursor = users.find().iterator();

        try {
            while(cursor.hasNext()) {
                Document current = cursor.next();
                result.put((String)current.get("id"), (String)current.get("picture"));
            }
        } finally {

        }

        client.close();

        return result;
    }
}
