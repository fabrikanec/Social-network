package graph;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

import graph.entity.*;

public class OgmTest {

    public static void main(final String[] args) throws Exception {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-neo4j");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
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
        tx.commit();
        em.clear();
        em.getTransaction().begin();
        Query query = em.createQuery("from Person p");
        System.out.println("Persons:" + query.getResultList().size());
        query = em.createQuery("from Friend i");
        System.out.println("Items:" + query.getResultList().size());
        em.getTransaction().commit();
        em.close();
        emf.close();
        printDbContents();
    }

    private static void printDbContents() {
        final GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("/home/cezar/temp/ogm-neo4j");
        try(final Transaction tx = graphDb.beginTx()) {
            for (final Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
                System.out.print(node.getId() + " ");
                for (final String key : node.getPropertyKeys()) {
                    System.out.print(key + " - " + node.getProperty(key) + ", ");
                }
                System.out.print("\n");
            }
            tx.success();
        } finally {
            graphDb.shutdown();
        }
    }
}