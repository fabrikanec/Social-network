package socialDist.cassandra;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import socialDist.cassandra.accessors.UserAccessor;
import socialDist.util.CassandraLink;
import socialDist.util.Inject;
import socialDist.util.Injector;

import java.util.UUID;

/**
 * Created by izoomko on 5/17/17.
 */
@Accessor
public class Users extends Injector {
    @Inject
    CassandraLink link;

    MappingManager manager;
    Mapper<User> mapper;
    socialDist.cassandra.accessors.UserAccessor accessor;

    public Users() {
        mapper = link.getManager(User.class);
        manager = link.getManager();
        accessor = manager.createAccessor(UserAccessor.class);
    }

    public Result<User> getUsers() {
        return accessor.getAll();
    }

    public User getUserById(UUID id) {
        return accessor.getById(id);
    }
}
