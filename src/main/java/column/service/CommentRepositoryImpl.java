package main.java.column.service;

import main.java.column.repository.CommentRepository;
import main.java.column.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by cezar on 5/23/17.
 */
@Service
public class CommentRepositoryImpl implements CommentRepository {
    
    private CommentRepository commentRepository;


    @Autowired
    public void setCommentRepository(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public <S extends Comment> S insert(S s) {
        return commentRepository.insert(s);
    }

    public <S extends Comment> S save(S s) {
        return commentRepository.save(s);
    }

    public <S extends Comment> Iterable<S> save(Iterable<S> iterable) {
        return commentRepository.save(iterable);
    }

    @Override
    public Comment findOne(MapId mapId) {
        return commentRepository.findOne(mapId);
    }

    @Override
    public boolean exists(MapId mapId) {
        return commentRepository.exists(mapId);
    }

    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Iterable<Comment> findAll(Iterable<MapId> iterable) {
        return commentRepository.findAll(iterable);
    }

    @Override
    public long count() {
        return commentRepository.count();
    }

    @Override
    public void delete(MapId mapId) {
        commentRepository.delete(mapId);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void delete(Iterable<? extends Comment> iterable) {
        commentRepository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}
