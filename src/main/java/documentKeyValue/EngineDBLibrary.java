package main.java.documentKeyValue;

import com.mongodb.*;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EngineDBLibrary {
    private MongoClient mongo;
    private DB db;
    private Jedis jedis;

    EngineDBLibrary(String url, Integer port) {
        this.jedis = new Jedis(url);
        this.mongo = new MongoClient(url, port);
        this.db = mongo.getDB("EngineDB");
    }

    public Integer createArticles(String name, String description, String text, Integer creatorId) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject document = new BasicDBObject();
            document.put("name", name);
            document.put("description", description);
            document.put("text", text);
            document.put("creatorId", creatorId);
            document.put("events", new HashMap<String, BasicDBObject>());
            table.insert(document);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public Integer updateArticles(String name, String description, String text) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject query = new BasicDBObject();
            query.put("name", name);
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("name", name);
            newDocument.put("description", description);
            newDocument.put("text", text);
            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);

            table.update(query, updateObj);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public Integer deleteArticles(String name) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", name);

            table.remove(searchQuery);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public void viewArticles(String name) throws Exception {
        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", name);
            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                DBObject article = cursor.next();
                System.out.println(article);
                jedis.set(String.valueOf(article.hashCode()), String.valueOf(article));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public Integer createEvent(String articleName, String name, Integer authorId, String content,
                               Integer rating, Date creationDate) throws Exception {
        Integer res;
        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", articleName);
            DBCursor cursor = table.find(searchQuery);
            BasicDBObject document = new BasicDBObject();
            document.put("name", name);
            document.put("authorId", authorId);
            document.put("content", content);
            document.put("rating", rating);
            document.put("creationDate", creationDate);
            while (cursor.hasNext()) {
                DBObject event = cursor.next();
                if( event.keySet().contains("events")) {
                    ((HashMap<String, BasicDBObject>) event.get("events")).put(name, document);
                }
                jedis.set(String.valueOf(event.hashCode()), String.valueOf(event));
            }
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public Integer updateEvent(String name, String upname, String content,
                               Integer rating, Date creationDate) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("events");
            BasicDBObject query = new BasicDBObject();
            query.put("name", name);
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("name", upname);
            newDocument.put("content", content);
            newDocument.put("rating", rating);
            newDocument.put("creationDate", creationDate);
            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);

            table.update(query, updateObj);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public Integer deleteEvent(String name) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("events");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", name);

            table.remove(searchQuery);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public void viewEvents(String name) throws Exception {

        try {
            DBCollection table = db.getCollection("events");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", name);
            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                DBObject event = cursor.next();
                System.out.println(event);
                jedis.set(String.valueOf(event.hashCode()), String.valueOf(event));
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
