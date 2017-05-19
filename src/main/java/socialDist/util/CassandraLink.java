package socialDist.util;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.RoundRobinPolicy;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Created by izoomko on 5/17/17.
 */
public class CassandraLink<T> extends Injector {
    private static CassandraLink link = null;

    private Cluster cluster;
    private Session session;
    private MappingManager manager;

    @Inject GoogleAPI api;

    private CassandraLink() throws IOException, GeneralSecurityException {
        Cluster.Builder builder = Cluster.builder().withLoadBalancingPolicy(new RoundRobinPolicy())
                .withAuthProvider(new PlainTextAuthProvider("izoomko", "izoomko"));
        List<String> ips = api.getIP();
        for(String i : ips) {
            if (i != null) {
                builder.addContactPoints(i);
            }
        }
        cluster = builder.build();
        session = cluster.connect("sdb");
        manager = new MappingManager(session);
    }

    @Nonnull
    public static CassandraLink getInstance() throws IOException, GeneralSecurityException {
        if (link != null) {
            return link;
        }
        return new CassandraLink();
    }

    public Mapper<T> getManager(Class<T> klass) {
        return manager.mapper(klass);
    }

    public MappingManager getManager() {
        return manager;
    }
}
