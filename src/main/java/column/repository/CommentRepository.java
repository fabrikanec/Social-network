package main.java.column.repository;

import main.java.column.model.Comment;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cezar on 5/18/17.
 */
@Repository
public interface CommentRepository extends CassandraRepository<Comment> {

    /*<S extends Comment>Iterable<S> save(Iterable<S> iterable);

    <S extends Comment> S save(S s);

    @Query("select * from comment where comment_id = ?0")
    Comment findOne(UUID commentId);

    @Query("select * from comment where comment_id = ?0 and event_id = ?1")
    Comment findOne(UUID commentId, UUID eventId);

    @Query("select * from comment where comment_id = ?0 and event_id = ?1 and article_id = ?2")
    Comment findOne(UUID commentId, UUID eventId, UUID articleID);


    boolean exists(UUID commentId);

    Iterable<Comment> findAll();

    long count();

    void delete(MapId mapId);

    void delete(Comment comment);

    void delete(Iterable<? extends Comment> iterable);

    void deleteAll();

//    boolean exists(MapId mapId);*/




    //VUUUUUUUUUUUUUUUUUUUUUUUUUUU




}
