package main.java.column;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import main.java.column.config.CassandraConfig;
import main.java.column.model.*;
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
        cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9042).build();
        final Session session = cluster.connect(KEYSPACE);
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


        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), User.class, new HashMap<String, Object>());

        template.insert(new User(UUIDs.timeBased(), UUIDs.timeBased(),
                Arrays.asList(new Comment(UUIDs.timeBased(),UUIDs.timeBased(),UUIDs.timeBased(),UUIDs.timeBased(), "lol")),
                Arrays.asList(new Event(UUIDs.timeBased(), UUIDs.timeBased(), "str", "event", "strevent")),
                Arrays.asList(new Article(UUIDs.timeBased(), "art", "arter", new HashSet<String>(){{add("set");}})),
                Arrays.asList(new Message(UUIDs.timeBased(), UUIDs.timeBased(), "msg", new Date())),
                new HashSet<>(Arrays.asList(UUIDs.timeBased())),
                "pass", "fst", "lst"));

        //QueryBuilder qb = new QueryBuilder(cluster);//
        Select s = QueryBuilder.select().from("users");
        System.out.println(template.count(User.class));

        //template.truncate("users");
        System.out.println("done");
        if (new Scanner(System.in).nextLine().contains("exit")) {
            template.truncate("users");
        }
    }

    public static void main(String[] args) throws Exception {
//        preload();
        App app = new App();
        app.run();

    }
}
