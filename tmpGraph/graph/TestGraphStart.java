package graph;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import static org.hibernate.engine.transaction.jta.platform.internal.JBossStandAloneJtaPlatform.JBOSS_TM_CLASS_NAME;

/**
 * Created by cezar on 5/6/17.
 */
public class TestGraphStart {

    public static void main(String[] args) throws Exception {
        //accessing JBoss's Transaction can be done differently but this one works nicely
        TransactionManager tm = getTransactionManager();

        //build the EntityManagerFactory as you would build in in Hibernate ORM
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "tmpGraph/graph");


        //Persist entities the way you are used to in plain JPA
        tm.begin();
        //logger.infof("About to store dog and breed");
        EntityManager em = emf.createEntityManager();
        /*Breed collie = new Breed();
        collie.setName("Collie");
        em.persist(collie);
        Dog dina = new Dog();
        dina.setName("Dina");
        dina.setBreed(collie);
        em.persist(dina);
        Long dinaId = dina.getId();*/
        em.flush();
        em.close();
        tm.commit();



        //Retrieve your entities the way you are used to in plain JPA
        /*tm.begin();
        logger.infof("About to retrieve dog and breed");
        em = emf.createEntityManager();
        dina = em.find(Dog.class, dinaId);
        logger.infof("Found dog %s of breed %s", dina.getName(), dina.getBreed().getName());
        em.flush();
        em.close();
        tm.commit();
        */

        emf.close();


    }

    public static TransactionManager getTransactionManager() throws Exception {
        Class<?> tmClass = TestGraphStart.class.getClassLoader().loadClass(JBOSS_TM_CLASS_NAME);
        return (TransactionManager) tmClass.getMethod("transactionManager").invoke(null);
    }
}