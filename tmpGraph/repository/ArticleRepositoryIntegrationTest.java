package repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baeldung.spring.data.cassandra.config.CassandraConfig;
import org.baeldung.spring.data.cassandra.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class ArticleRepositoryIntegrationTest {
    private static final Log LOGGER = LogFactory.getLog(ArticleRepositoryIntegrationTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    public static final String DATA_TABLE_NAME = "user";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

    //
    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
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
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Article.class, new HashMap<String, Object>());
    }

    @Test
    public void whenSavingArticle_thenAvailableOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        articleRepository.save(ImmutableSet.of(javaArticle));
        final Iterable<Article> articles = articleRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        assertEquals(javaArticle.getId(), articles.iterator().next().getId());
    }

    @Test
    public void whenUpdatingArticles_thenAvailableOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        articleRepository.save(ImmutableSet.of(javaArticle));
        final Iterable<Article> articles = articleRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        javaArticle.setTitle("Head First Java Second Edition");
        articleRepository.save(ImmutableSet.of(javaArticle));
        final Iterable<Article> updateArticles = articleRepository.findByTitleAndPublisher("Head First Java Second Edition", "O'Reilly Media");
        assertEquals(javaArticle.getTitle(), updateArticles.iterator().next().getTitle());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void whenDeletingExistingArticles_thenNotAvailableOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        articleRepository.save(ImmutableSet.of(javaArticle));
        articleRepository.delete(javaArticle);
        final Iterable<Article> articles = articleRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        assertNotEquals(javaArticle.getId(), articles.iterator().next().getId());
    }

    @Test
    public void whenSavingArticles_thenAllShouldAvailableOnRetrieval() {
        final Article javaArticle = new Article(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Article dPatternArticle = new Article(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        articleRepository.save(ImmutableSet.of(javaArticle));
        articleRepository.save(ImmutableSet.of(dPatternArticle));
        final Iterable<Article> articles = articleRepository.findAll();
        int articleCount = 0;
        for (final Article article : articles) {
            articleCount++;
        }
        assertEquals(articleCount, 2);
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
