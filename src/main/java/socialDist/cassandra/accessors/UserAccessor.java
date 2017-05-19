package socialDist.cassandra.accessors;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import socialDist.cassandra.User;

import java.util.UUID;

/**
 * Created by izoomko on 5/17/17.
 */
@Accessor
public interface UserAccessor {
    @Query("SELECT * FROM users")
    Result<User> getAll();

    @Query("SELECT * FROM users WHERE id = ?")
    User getById(UUID id);
}
