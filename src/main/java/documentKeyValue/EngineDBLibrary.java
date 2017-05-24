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
        this.db = mongo.getDB("EngineDB"); //cezar cezar
    }



    public Integer createArticles(Long article_id, String publisher, String title, String text) throws Exception {
        Integer res;
        try {
            if (jedis.get(String.valueOf(article_id)) != null) {
                System.out.println("Duplicate.");
                return null;
            }
            DBCollection table = db.getCollection("articles");
            BasicDBObject document = new BasicDBObject();
            document.put("article_id", article_id);
            document.put("publisher", publisher);
            document.put("title", title);
            document.put("text", text);
            table.insert(document);
            jedis.set(String.valueOf(article_id), document.toJson());
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public Integer updateArticles(Long article_id, String publisher, String title, String text) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject query = new BasicDBObject();
            query.put("title", title);
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("article_id", article_id);
            newDocument.put("publisher", publisher);
            newDocument.put("title", title);
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

    public Integer deleteArticles(Long article_id) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("article_id", article_id);

            table.remove(searchQuery);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public void viewArticles(Long article_id) throws Exception {
        try {
            DBCollection table = db.getCollection("articles");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("article_id", article_id);
            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                DBObject article = cursor.next();
                System.out.println(article);
                jedis.set(String.valueOf(article), String.valueOf(article));
            }
        } catch (Exception e) {
            throw e;
        }
    }
    

    public Integer createEvent(Long event_id, Long user_id, String name, String text,
                                                                         String subj) throws Exception {
        Integer res;
        try {
            if (jedis.get(String.valueOf(event_id)) != null) {
                System.out.println("Duplicate.");
                return null;
            }
            DBCollection table = db.getCollection("events");
            BasicDBObject document = new BasicDBObject();
            document.put("event_id", event_id);
            document.put("user_id", user_id);
            document.put("name", name);
            document.put("text", text);
            document.put("subj", subj);
            table.insert(document);
            jedis.set(String.valueOf(event_id), document.toJson());
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public Integer updateEvent(Long event_id, Long user_id, String name, String text,
                               String subj) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("events");
            BasicDBObject query = new BasicDBObject();
            query.put("name", name);
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("event_id", event_id);
            newDocument.put("user_id", user_id);
            newDocument.put("name", name);
            newDocument.put("text", text);
            newDocument.put("subj", subj);
            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);

            table.update(query, updateObj);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public Integer deleteEvent(Long id) throws Exception {
        Integer res;

        try {
            DBCollection table = db.getCollection("events");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("event_id", id);

            table.remove(searchQuery);
            res = 0;
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public void viewEvents(Long id) throws Exception {

        try {
            DBCollection table = db.getCollection("events");
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("event_id", id);
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
