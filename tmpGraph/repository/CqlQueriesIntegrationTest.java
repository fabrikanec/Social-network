package repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.transport.TTransportException;
import org.baeldung.spring.data.cassandra.config.CassandraConfig;
import org.baeldung.spring.data.cassandra.model.Article;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class CqlQueriesIntegrationTest {
    private static final Log LOGGER = LogFactory.getLog(CqlQueriesIntegrationTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    public static final String DATA_TABLE_NAME = "article";

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @Autowired
    private CassandraOperations cassandraTemplate;

    //

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(25000);
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        LOGGER.info("KeySpace created and activated.");
        Thread.sleep(5000);
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Article.class, new HashMap<String, Object>());
    }

    @Test
    public void whenSavingArticle_thenAvailableOnRetrieval_usingQueryBuilder() {
        final UUID uuid = UUIDs.timeBased();
        final Insert insert = QueryBuilder.insertInto(DATA_TABLE_NAME).value("id", uuid).value("title", "Head First Java").value("publisher", "OReilly Media").value("tags", ImmutableSet.of("Software"));
        cassandraTemplate.execute(insert);
        final Select select = QueryBuilder.select().from("article").limit(10);
        final Article retrievedArticle = cassandraTemplate.selectOne(select, Article.class);
        assertEquals(uuid, retrievedArticle.getId());
    }

    @Test
    public void whenSavingArticle_thenAvailableOnRetrieval_usingCQLStatements() {
        final UUID uuid = UUIDs.timeBased();
        final String insertCql = "insert into article (id, title, publisher, tags) values " + "(" + uuid + ", 'Head First Java', 'OReilly Media', {'Software'})";
        cassandraTemplate.execute(insertCql);
        final Select select = QueryBuilder.select().from("article").limit(10);
        final Article retrievedArticle = cassandraTemplate.selectOne(select, Article.class);
        assertEquals(uuid, retrievedArticle.getId());
    }

    @Test
    public void whenSavingArticle_thenAvailableOnRetrieval_usingPreparedStatements() throws InterruptedException {
        final UUID uuid = UUIDs.timeBased();
        final String insertPreparedCql = "insert into article (id, title, publisher, tags) values (?, ?, ?, ?)";
        final List<Object> singleArticleArgsList = new ArrayList<>();
        final List<List<?>> articleList = new ArrayList<>();
        singleArticleArgsList.add(uuid);
        singleArticleArgsList.add("Head First Java");
        singleArticleArgsList.add("OReilly Media");
        singleArticleArgsList.add(ImmutableSet.of("Software"));
        articleList.add(singleArticleArgsList);
        cassandraTemplate.ingest(insertPreparedCql, articleList);
        // This may not be required, just added to avoid any transient issues
        Thread.sleep(5000);
        final Select select = QueryBuilder.select().from("article");
        final Article retrievedArticle = cassandraTemplate.selectOne(select, Article.class);
        assertEquals(uuid, retrievedArticle.getId());
    }

    @After
    public void dropTable() {
        adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

}
