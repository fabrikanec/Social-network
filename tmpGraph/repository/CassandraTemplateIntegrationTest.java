package repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.transport.TTransportException;
import org.baeldung.spring.data.cassandra.config.CassandraConfig;
import org.baeldung.spring.data.cassandra.model.Article;

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

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class CassandraTemplateIntegrationTest {
    private static final Log LOGGER = LogFactory.getLog(CassandraTemplateIntegrationTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    public static final String DATA_TABLE_NAME = "user";

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @Autowired
    private CassandraOperations cassandraTemplate;

    //

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        LOGGER.info("KeySpace created and activated.");
        Thread.sleep(5000);
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Article.class, new HashMap<String, Object>());
    }

    @Test
    public void whenSavingArticle_thenAvailableOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaArticle);
        final Select select = QueryBuilder.select().from("article").where(QueryBuilder.eq("title", "Head First Java")).and(QueryBuilder.eq("publisher", "O'Reilly Media")).limit(10);
        final Article retrievedArticle = cassandraTemplate.selectOne(select, Article.class);
        assertEquals(javaArticle.getId(), retrievedArticle.getId());
    }

    @Test
    public void whenSavingArticles_thenAllAvailableOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Article dPatternArticle = new Article(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final List<Article> articleList = new ArrayList<>();
        articleList.add(javaArticle);
        articleList.add(dPatternArticle);
        cassandraTemplate.insert(articleList);

        final Select select = QueryBuilder.select().from("article").limit(10);
        final List<Article> retrievedArticles = cassandraTemplate.select(select, Article.class);
        assertThat(retrievedArticles.size(), is(2));
        assertEquals(javaArticle.getId(), retrievedArticles.get(0).getId());
        assertEquals(dPatternArticle.getId(), retrievedArticles.get(1).getId());
    }

    @Test
    public void whenUpdatingArticle_thenShouldUpdatedOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaArticle);
        final Select select = QueryBuilder.select().from("article").limit(10);
        final Article retrievedArticle = cassandraTemplate.selectOne(select, Article.class);
        retrievedArticle.setTags(ImmutableSet.of("Java", "Programming"));
        cassandraTemplate.update(retrievedArticle);
        final Article retrievedUpdatedArticle = cassandraTemplate.selectOne(select, Article.class);
        assertEquals(retrievedArticle.getTags(), retrievedUpdatedArticle.getTags());
    }

    @Test
    public void whenDeletingASelectedArticle_thenNotAvailableOnRetrieval() throws InterruptedException {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "OReilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaArticle);
        cassandraTemplate.delete(javaArticle);
        final Select select = QueryBuilder.select().from("article").limit(10);
        final Article retrievedUpdatedArticle = cassandraTemplate.selectOne(select, Article.class);
        assertNull(retrievedUpdatedArticle);
    }

    @Test
    public void whenDeletingAllArticles_thenNotAvailableOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Article dPatternArticle = new Article(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaArticle);
        cassandraTemplate.insert(dPatternArticle);
        cassandraTemplate.deleteAll(Article.class);
        final Select select = QueryBuilder.select().from("article").limit(10);
        final Article retrievedUpdatedArticle = cassandraTemplate.selectOne(select, Article.class);
        assertNull(retrievedUpdatedArticle);
    }

    @Test
    public void whenAddingArticles_thenCountShouldBeCorrectOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Article dPatternArticle = new Article(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaArticle);
        cassandraTemplate.insert(dPatternArticle);
        final long articleCount = cassandraTemplate.count(Article.class);
        assertEquals(2, articleCount);
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
