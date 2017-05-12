package graph.neo4j;

import org.junit.Ignore;
import org.junit.Test;



public class Neo4jOgmLiveTest {

    @Ignore
    @Test
    public void testOgm() {
        /*Configuration conf = new Configuration();
        conf.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");

        SessionFactory factory = new SessionFactory(conf, "com.baeldung.spring.data.neo4j.domain");
        Session session = factory.openSession();

        Car tesla = new Car("tesla", "modelS");
        Company baeldung = new Company("baeldung");

        baeldung.setCar(tesla);

        session.save(baeldung);

        Assert.assertEquals(1, session.countEntitiesOfType(Company.class));

        Map<String, String> params = new HashMap<>();
        params.put("make", "tesla");
        Result result = session.query("MATCH (car:Car) <-[:owns]- (company:Company)" +
                " WHERE car.make=$make" +
                " RETURN company", params);

        Map<String, Object> firstResult = result.iterator().next();

        Assert.assertEquals(firstResult.size(), 1);

        Company actual = (Company) firstResult.get("company");
        Assert.assertEquals(actual.getName(), baeldung.getName());*/
    }
}
