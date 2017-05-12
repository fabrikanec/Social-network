package graph;

import graph.entity.Friend;
import graph.entity.User;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.TransactionManager;
import java.util.HashSet;
import java.util.Set;

public class OgmJtaTest {

    public static TransactionManager extractTransactionManager(EntityManagerFactory factory) {
        SessionFactoryImplementor sessionFactory = factory.unwrap(SessionFactoryImplementor.class);
        return sessionFactory.getServiceRegistry().getService(JtaPlatform.class).retrieveTransactionManager();
    }

    public static void main(final String[] args) throws Exception {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("tmpGraph/graph");
        final TransactionManager transactionManager = extractTransactionManager(emf);
        transactionManager.begin();
        EntityManager em = emf.createEntityManager();
        User person = new User("John", "Doe");
        em.persist(person);
        final Friend iphone = new Friend("iPhone");
        em.persist(iphone);
        final Friend ipod = new Friend("iPod");
        em.persist(ipod);
        person.addItem(iphone);
        person.addItem(ipod);
        Set<User> persons = new HashSet<>();
        persons.add(person);
        iphone.setUsers(persons);
        ipod.setUsers(persons);
        em.merge(person);
        transactionManager.commit();
        em.close();

        em = emf.createEntityManager();
        transactionManager.begin();
        Query query = em.createQuery("from User p");
        System.out.println("Users:" + query.getResultList().size());
        query = em.createQuery("from Friend i");
        System.out.println("Items:" + query.getResultList().size());
        transactionManager.commit();
        em.close();
        emf.close();
        printDbContents();
    }

    private static void printDbContents() {
        final GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("/home/cezar/temp/graphDB");
        Transaction tx = graphDb.beginTx();
        try {
            for (final Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
                System.out.print(node.getId() + " ");
                for (final String key : node.getPropertyKeys()) {
                    System.out.print(key + " - " + node.getProperty(key) + ", ");
                }
                System.out.print("\n");
            }
            tx.success();
        } finally {
            tx.finish();
            graphDb.shutdown();
        }
    }
}