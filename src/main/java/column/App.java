package main.java.column;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import main.java.column.config.CassandraConfig;
import main.java.column.model.*;
import org.apache.commons.collections4.iterators.IteratorEnumeration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.cassandra.core.cql.generator.CreateUserTypeCqlGenerator;
import org.springframework.cassandra.core.keyspace.CreateUserTypeSpecification;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.ProtocolVersion;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by cezar on 5/22/17.
 */
@ComponentScan(basePackages = { "main.java.column.config" })
public class App {

    private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS columnkeyspace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";
    private static final String KEYSPACE_ACTIVATE_QUERY = "USE columnkeyspace;";
    private static final String DATA_TABLE_NAME = "users";
    private static final String KEYSPACE = "columnkeyspace";
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(
            CassandraConfig.class);

    private CassandraMappingContext cassandraMappingContext;
    private CassandraConverter cassandraConverter;
    private final CassandraAdminOperations adminTemplate;
    private CassandraOperations template;
    private final Cluster cluster;

    {
        cassandraMappingContext = (CassandraMappingContext) context.getBean("context");
        cassandraConverter = (CassandraConverter) context.getBean("converter");
        cluster = Cluster.builder().addContactPoints("172.17.0.2").withPort(9042).build();
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        adminTemplate = new CassandraAdminTemplate(session, cassandraConverter);
        template = new CassandraTemplate(session, cassandraConverter);
    }


    public static void preload() throws Exception {

        /*session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);*/
        /*String cqlStatement = "CREATE TABLE users (" +
                " username varchar PRIMARY KEY," +
                " password varchar " +
                ");";
        session.execute(cqlStatement);

        cqlStatement = "INSERT INTO testKeySpace.users (username, password) " +
                "VALUES ('Serenity', 'fa3dfQefx')";
        session.execute(cqlStatement);

        cqlStatement = "SELECT * FROM users;";
        System.out.println(session.execute(cqlStatement));*/

    }
    public void run() throws Exception {
        /*adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Book.class, new HashMap<String, Object>());
        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaBook);
        final Select select = QueryBuilder.select().from("book").where(QueryBuilder.eq("title", "Head First Java")).and(QueryBuilder.eq("publisher", "O'Reilly Media")).limit(10);
        final Book retrievedBook = cassandraTemplate.selectOne(select, Book.class);
        assertEquals(javaBook.getId(), retrievedBook.getId());*/
        CreateUserTypeSpecification userTypeSpecification = cassandraMappingContext.
                getCreateUserTypeSpecificationFor(cassandraMappingContext.getPersistentEntity(Article.class))
                .ifNotExists(true);
        adminTemplate.execute(CreateUserTypeCqlGenerator.toCql(userTypeSpecification));
        userTypeSpecification = cassandraMappingContext.
                getCreateUserTypeSpecificationFor(cassandraMappingContext.getPersistentEntity(Comment.class))
                .ifNotExists(true);
        adminTemplate.execute(CreateUserTypeCqlGenerator.toCql(userTypeSpecification));
        userTypeSpecification = cassandraMappingContext.
                getCreateUserTypeSpecificationFor(cassandraMappingContext.getPersistentEntity(Message.class))
                .ifNotExists(true);
        adminTemplate.execute(CreateUserTypeCqlGenerator.toCql(userTypeSpecification));
        userTypeSpecification = cassandraMappingContext.
                getCreateUserTypeSpecificationFor(cassandraMappingContext.getPersistentEntity(Event.class))
                .ifNotExists(true);
        adminTemplate.execute(CreateUserTypeCqlGenerator.toCql(userTypeSpecification));
        userTypeSpecification = cassandraMappingContext.
                getCreateUserTypeSpecificationFor(cassandraMappingContext.getPersistentEntity(Community.class))
                .ifNotExists(true);
        adminTemplate.execute(CreateUserTypeCqlGenerator.toCql(userTypeSpecification));


        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME),

                User.class, new HashMap<String, Object>());


        /*UUID userId = UUIDs.timeBased();
        UUID userId1 = UUIDs.timeBased();

        System.out.println(userId);
        System.out.println(userId1);
        User user = new User(userId, UUIDs.timeBased(),
                "password", "firstName", "lastName");

        User user1 = new User(userId1, UUIDs.timeBased(),
                "password1", "firstName1", "lastName1");

        UUID message1 = UUIDs.timeBased();
        user1.addMessage(new Message(message1, user1.getUserId(), "kek", new Date()));
        user.addMessage(new Message(message1, user1.getUserId(), "kek", new Date()));*/

        /*template.insert(user);
        template.insert(user1);*/
       /* Select s = QueryBuilder.select("userMessages").from("users");
        final Select select = QueryBuilder.select("userMessages").from("users").where(QueryBuilder.eq("user_id", user1)).orderBy();
        final User getter = template.selectOne(select, User.class);
        getter.getUserMessages().forEach(System.out::println);
*/

        /*UUID userTestId = UUIDs.timeBased();
        User user = new User(userTestId, UUIDs.timeBased(),
                "pass", "fst", "lst");
        template.insert(user);

        user.addComment(new Comment(UUIDs.timeBased(), UUIDs.timeBased(), UUIDs.timeBased(), UUIDs.timeBased(), "lol"));

        template.update(user);
        //QueryBuilder qb = new QueryBuilder(cluster);//
        Select s = QueryBuilder.select().from("users");
        System.out.println(template.count(User.class));
        ;
        //template.truncate("users");
        System.out.println("done");
        if (new Scanner(System.in).nextLine().contains("exit")) {
            template.truncate("users");
        }*/

        Set<UUID> uuids = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            UUID user_id = UUIDs.timeBased();
            uuids.add(user_id);
            UUID token = UUIDs.timeBased();
            UUID comment_id = UUIDs.timeBased();
            UUID article_id = UUIDs.timeBased();
            UUID event_id = UUIDs.timeBased();
            UUID community_id = UUIDs.timeBased();
            UUID message_id = UUIDs.timeBased();

            User user = new User(user_id, token,
                    "password" + String.valueOf(i), "firstName" + String.valueOf(i), "lastName" + String.valueOf(i));

            user.addComment(new Comment(comment_id, user_id, article_id, event_id, "text" + i));
            user.addArticle(new Article(article_id, "title" + i, "publisher" + i, "text" + i));
            user.addCommunity(new Community(community_id, "name" + i));
            user.addEvent(new Event(event_id, user_id, "name" + i, "text" + i, "subj" + i));
            Iterator<UUID> iter = uuids.iterator();
            for (int j = 0; j < 100; j++) {
                if (iter.hasNext()) {
                    user.addFriend(iter.next());
                }
            }
            user.addMessage(new Message(message_id, user_id, "text" + i, new Date()));
            template.insert(user);
        }
        System.out.println(666);
    }


    public static void main(String[] args) throws Exception {
//        preload();
        App app = new App();
        app.run();

    }
}
