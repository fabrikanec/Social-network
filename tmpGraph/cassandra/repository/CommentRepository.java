package org.baeldung.spring.data.cassandra.repository;

import org.baeldung.spring.data.cassandra.model.Comment;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapId;

import java.util.UUID;

/**
 * Created by cezar on 5/18/17.
 */
public interface CommentRepository extends CassandraRepository<Comment> {

    <S extends Comment>Iterable<S> save(Iterable<S> iterable);

    Comment save(Comment s);

    Comment findOne(UUID commentId);

    Comment findOne(UUID commentId, UUID eventId);

    Comment findOne(UUID commentId, UUID eventId, UUID articleID);

    boolean exists(UUID commentId);

    Iterable<Comment> findAll();

    long count();

    void delete(MapId mapId);

    void delete(Comment comment);

    void delete(Iterable<? extends Comment> iterable);

    void deleteAll();

    boolean exists(MapId mapId);
}
