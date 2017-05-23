package main.java.column.repository;

import com.datastax.driver.core.ResultSet;

import main.java.column.model.User;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@NoRepositoryBean
@Repository
public interface UserRepository extends CassandraRepository<User> {

   /* List<User> findFolksWithLastnameAsList(String lastname);

    ResultSet findFolksWithLastnameAsResultSet(String last);

    @Query("SELECT * FROM users WHERE lastName = ?0")
    User[] findFolksWithLastnameAsArray(String lastname);

    User findSingle(String last, String first);

    List<Map<String, Object>> findFolksWithLastnameAsListOfMapOfStringToObject(String last);

    Optional<User> findOptionalWithLastnameAndFirstname(String last, String first);

    Stream<User> findAllUsers();

    List<Comment> findUserComments(String last, String first);

    List<Message> findMessages(String last, String first);

    List<Article> findArticles(String last, String first);

    boolean exists(MapId mapId);

    <S extends User> S save(S user);

    <S extends User> Iterable<S> save(Iterable<S> iterable);

    long count();

    void delete(MapId mapId);

    void delete(User user);

    void delete(Iterable<? extends User> iterable);

    void deleteAll();*/
}
