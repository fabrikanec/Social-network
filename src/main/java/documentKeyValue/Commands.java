package main.java.documentKeyValue;

import com.mongodb.MongoClient;
import redis.clients.jedis.Jedis;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.*;
import java.sql.Date;

@FunctionalInterface
interface Operation {
    int call(String... expr) throws Exception;
}

public class Commands {
    private static final String URL = "localhost";
    private static final Integer PORT = 27017;
    private List<Operation> ops;
    private Jedis jedis = new Jedis(URL);
    private EngineDBLibrary lib = new EngineDBLibrary(URL, PORT);

    public enum Opcodes {
        ADD, UPDATE, DELETE, LIST
    }

    {
        ops = new ArrayList<>();
        ops.add(this::add);
        ops.add(this::update);
        ops.add(this::delete);
        ops.add(this::list);
    }

    public List<Operation> getOps() {
        return ops;
    }

    public int add(String... params) throws Exception {
        int res = 0;
        System.err.println("add called");
        switch (params[0]) {
            case "Events":
                try {
                    res = lib.createEvent(params[1], params[2], Integer.valueOf(params[3]), params[4], Integer.valueOf(params[5]),
                            java.sql.Date.valueOf(params[6]));

                    jedis.set(String.valueOf(params[1].hashCode()), params[1] + " " + params[2] + " " + params[3] + " " +
                            Integer.valueOf(params[4]) + " " + params[5] + " " + params[6]);
                } catch (Exception e) {
                    throw e;
                }
                break;
            case "Articles":
                try {
                    res = lib.createArticles(params[1], params[2], params[3], Integer.valueOf(params[4]));
                    jedis.set(String.valueOf(params[1].hashCode()), params[1] + " " + params[2] + " " + params[3]);
                } catch (Exception e) {
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
                case "Events":
                    res = lib.updateEvent(params[1], params[2], params[3],
                            Integer.valueOf(params[4]), java.sql.Date.valueOf(params[5]));
                    break;
                case "Articles":
                    res = lib.updateArticles(params[1], params[2], params[3]);
                    break;
            }
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public int delete(String... params) throws Exception {
        jedis.del(params[1]);
        int res = 0;
        System.out.println("Called delete " + params[0]);
        try {
            switch (params[0]) {
                case "Events":
                    res = lib.deleteEvent(params[1]);
                    break;
                case "Articles":
                    res = lib.deleteArticles(params[1]);
                    break;

                /*case "users_com":
                    try {
                        res = lib.deleteUserFromCommunity(params[1], params[2]);
                        jedis.set(String.valueOf(params[1].hashCode()), params[1] + " " + params[2]);
                    } catch (Exception e) {
                        throw e;
                    }*/
            }
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    public int list(String... params) throws Exception {
        System.out.println("Called list");
        switch (params[0]) {
            case "Events":
                try {
                    lib.viewEvents(params[1]);
                } catch (Exception e) {
                    throw e;
                }
                break;
            case "Articles":
                try {
                    lib.viewArticles(params[1]);
                } catch (Exception e) {
                    throw e;
                }
                break;
            case "articles":
                try {
                    lib.viewArticles(params[1]);
                    jedis.set(String.valueOf(params[1].hashCode()), params[1]);
                } catch (Exception e) {
                    throw e;
                }
                break;
        }
        return 0;
    }
}