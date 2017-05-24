package main.java.relation.dbService;

import main.java.relation.dbService.dao.*;
import main.java.relation.dbService.dataSets.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Date;
import java.util.List;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

class CallableStatementImp {
    Connection connection = null;

    public CallableStatementImp() {
        try {
            // Loading the driver
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Creating a function to get a connection
    public Connection createConnection() {
        Connection con = null;
        // checking connection
        if (connection != null) {
            System.out.println("Can't creaate a connection");
            return connection;
        } else {
            try {
                // Getting connection
                con = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/cezar", "cezar",
                        "cezar");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return con;
    }
}

public class DBService implements DBServiceInterface {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final SessionFactory sessionFactory;

    public DBService() {
        //Configuration configuration = getH2Configuration();
        Configuration configuration = getPostgresqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    @SuppressWarnings("UnusedDeclaration")
    private Configuration getPostgresqlConfiguration() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(ArticleDataSet.class);
        configuration.addAnnotatedClass(FriendDataSet.class);
        configuration.addAnnotatedClass(EventDataSet.class);
        configuration.addAnnotatedClass(CommentDataSet.class);
        configuration.addAnnotatedClass(MessageDataSet.class);
        configuration.addAnnotatedClass(CommunityDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/cezar");
        configuration.setProperty("hibernate.connection.username", "cezar");
        configuration.setProperty("hibernate.connection.password", "cezar");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(ArticleDataSet.class);
        configuration.addAnnotatedClass(FriendDataSet.class);
        configuration.addAnnotatedClass(EventDataSet.class);
        configuration.addAnnotatedClass(CommentDataSet.class);
        configuration.addAnnotatedClass(MessageDataSet.class);
        configuration.addAnnotatedClass(CommunityDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    /** UserDataSet Logic */
    public UserDataSet getUserById(Long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UserDAO dao = new UserDAO(session);
            UserDataSet dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public UserDataSet getUser(String login) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UserDAO dao = new UserDAO(session);
            UserDataSet dataSet = dao.get(login);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public Long addUser(String login, String password) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserDAO dao = new UserDAO(session);
            Long id = dao.insertUser(login, password);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    /** ArticleDataSet Logic */
    public Long addArticle(Long id, char secure, String text, Date date) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ArticleDAO dao = new ArticleDAO(session);
            Long article_id = dao.insertArticle(id, secure, text, date);
            transaction.commit();
            session.close();
            return article_id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public ArticleDataSet getArticle(Long article_id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            ArticleDAO dao = new ArticleDAO(session);
            ArticleDataSet dataSet = dao.getArticle(article_id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    /** EventDataSet Logic */
    public Long addEvent(Long id, String name, String text, String subj) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            EventDAO dao = new EventDAO(session);
            Long event_id = dao.insertEvent(id, name, text, subj);
            transaction.commit();
            session.close();
            return event_id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public EventDataSet getEvent(Long event_id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            EventDAO dao = new EventDAO(session);
            EventDataSet dataSet = dao.getEvent(event_id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    /** FriendDataSet Logic */
    public Long addFriend(UserDataSet user, UserDataSet friend) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            FriendDAO dao = new FriendDAO(session);
            Long _friend_id = dao.addFriend(user, friend);
            transaction.commit();
            session.close();
            return _friend_id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public FriendDataSet getFriend(Long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            FriendDAO dao = new FriendDAO(session);
            FriendDataSet dataSet = dao.getFriend(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    /**CommentDataSet Logic */
    public Long addComment(Long id, Long id_article, Long id_event, String text) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CommentDAO dao = new CommentDAO(session);
            Long comment_id = dao.insertComment(id, id_article, id_event, text);
            transaction.commit();
            session.close();
            return comment_id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public CommentDataSet getComment(Long comment_id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CommentDAO dao = new CommentDAO(session);
            CommentDataSet dataSet = dao.getComment(comment_id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }


    /**MessageDataSet Logic */
    public Long addMessage(Long id, Boolean receaverMsgDeletedFlag, Boolean posterMsgDeletedFlag, String text, Date date) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            MessageDAO dao = new MessageDAO(session);
            Long message_id = dao.insertMessage(id, receaverMsgDeletedFlag, posterMsgDeletedFlag, text, date);
            transaction.commit();
            session.close();
            return message_id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public MessageDataSet getMessage(Long message_id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            MessageDAO dao = new MessageDAO(session);
            MessageDataSet dataSet = dao.getMessage(message_id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public Long count_msg() throws DBException {
        CallableStatementImp calllableStatement = new CallableStatementImp();
        try (Connection connection = calllableStatement.createConnection()) {
            // Creating Callable Statement
            String query = "select count_messages()";
            CallableStatement cs = connection.prepareCall(query);
            ResultSet rs = cs.executeQuery();
            // checking result
            if (rs == null) {
                System.out.println("Result is null");
                return 0L;
            } else {
                rs.next();
                String res = (rs.getString(1));
                Long resultCount = Long.parseLong(res);
                return resultCount;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0L;
        }
    }
    
    /** CommunityDataSet Logic */
    public Long addCommunity(String name) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CommunityDAO dao = new CommunityDAO(session);
            Long id = dao.insertCommunity(name);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public Long addUser(UserDataSet user, String communityName) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CommunityDAO dao = new CommunityDAO(session);
            Long user_id =  dao.addUser(user, communityName);
            transaction.commit();
            session.close();
            return user_id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<CommunityDataSet> getUsers(String com_name) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            CommunityDAO dao = new CommunityDAO(session);
            List<CommunityDataSet> dataSet = dao.getUsers(com_name);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public Long count_comm() throws DBException {
        CallableStatementImp callableStatement = new CallableStatementImp();
        try(Connection connection = callableStatement.createConnection()) {
            // Creating Callable Statement
            String query = "select count_comments()";
            CallableStatement cs = connection.prepareCall(query);
            ResultSet rs = cs.executeQuery();
            // checking result
            if (rs == null) {
                System.out.println("Result is null");
                return 0L;
            } else {
                rs.next();
                String res =  (rs.getString(1));
                Long resultCount = Long.parseLong(res);
                return resultCount;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0L;
        }
    }
}