package main.java.documentKeyValue;
import com.datastax.driver.core.utils.UUIDs;
import com.mongodb.MongoClient;
import redis.clients.jedis.Jedis;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.*;
import java.sql.Date;

interface Operation {
    int call(String... expr) throws Exception;
}

public class Commands {
    private static final String url = "127.0.0.1";
    private static final Integer port = 27017;
    private List<Operation> ops;
    private Jedis jedis = new Jedis(url);
    private EngineDBLibrary lib = new EngineDBLibrary(url, port);

    public enum Opcodes {
        ADD, UPDATE, DELETE, LIST
    }

    Commands() {
        ops = new ArrayList<>();
        ops.add(this::add);
        ops.add(this::update);
        ops.add(this::delete);
        ops.add(this::list);
    }

    public List<Operation> getOps() { return this.ops; }

    public int add(String... params) throws Exception {
        int res = 0;
        System.err.println("add called");
        // type
        switch(params[0]) {
            case "events":
                try {
                    res = lib.createEvent(Long.parseLong(params[1]), (Long.parseLong(params[2])), params[3], params[4],
                                                                                                params[5]);
                    jedis.set(String.valueOf(params[1]),params[3]+ " " + params[4] + " " + params[5]);
                } catch(Exception e) {
                    throw e;
                }
                break;
            case "articles":
                try {
                    res = lib.createArticles(Long.parseLong(params[1]), params[2], params[3], params[4]);
                    jedis.set(String.valueOf(Long.parseLong(params[1])), params[2]+ " " + params[3] + params[4]);
                } catch(Exception e) {
                    throw e;
                }
                break;
            default:
                System.err.println("Table ``" + params[0] + "'' is not found.");
        }

        return res;
    }
    public int update(String... params) throws Exception {
        jedis.del(params[1]);
        int res = 0;
        System.out.println("Called update.");
        try {
            switch (params[0]) {
                case "events":
                    res = lib.updateEvent(Long.parseLong(params[1]), (Long.parseLong(params[2])), params[3], params[4],
                            params[5]);
                    break;
                case "articles":
                    res = lib.updateArticles(Long.parseLong(params[1]), params[2], params[3], params[4]);
                    break;
            }
        } catch(Exception e) {
            throw e;
        }
        return res;
    }
    public int delete(String... params) throws Exception{
        jedis.del(params[1]);
        int res = 0;
        System.out.println("Called delete " + params[0]);
        try{
            switch(params[0]) {
                case "events":
                    res = lib.deleteEvent(Long.parseLong(params[1]));
                    break;
                case "articles":
                    res = lib.deleteArticles(Long.parseLong(params[1]));
                    break;
            }
        } catch(Exception e){
            throw e;
        }
        return res;
    }
    public int list(String... params) throws Exception{
        System.out.println("Called list");
        switch(params[0]) {
            case "events":
                try {
                    lib.viewEvents(Long.parseLong(params[1]));
                } catch (Exception e) {
                    throw e;
                }
                break;
            case "articles":
                try {
                    lib.viewArticles(Long.parseLong(params[1]));
                } catch (Exception e) {
                    throw e;
                }
                break;
        }
        return 0;
    }
}