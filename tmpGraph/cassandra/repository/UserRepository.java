package org.baeldung.spring.data.cassandra.repository;

import com.datastax.driver.core.ResultSet;
import org.baeldung.spring.data.cassandra.model.Article;
import org.baeldung.spring.data.cassandra.model.Comment;
import org.baeldung.spring.data.cassandra.model.Message;
import org.baeldung.spring.data.cassandra.model.User;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@EnableAspectJAutoProxy(proxyTargetClass = true)
//@NoRepositoryBean
@Repository
public interface UserRepository extends CassandraRepository<User> {

    List<User> findFolksWithLastnameAsList(String lastname);

    ResultSet findFolksWithLastnameAsResultSet(String last);

    User[] findFolksWithLastnameAsArray(String lastname);

    User findSingle(String last, String first);

    List<Map<String, Object>> findFolksWithLastnameAsListOfMapOfStringToObject(String last);

    Optional<User> findOptionalWithLastnameAndFirstname(String last, String first);

    Stream<User> findAllPeople();

    List<Comment> findUserComments(String last, String first);

    List<Message> findMessages(String last, String first);

    List<Article> findArticles(String last, String first);

    boolean exists(MapId mapId);

    User save(User user);

    <S extends User> Iterable<S> save(Iterable<S> iterable);

    long count();

    void delete(MapId mapId);

    void delete(User user);

    void delete(Iterable<? extends User> iterable);

    void deleteAll();
}
