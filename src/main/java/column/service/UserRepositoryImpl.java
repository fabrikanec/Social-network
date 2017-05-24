package main.java.column.service;

import main.java.column.repository.UserRepository;
import main.java.column.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cezar on 5/23/17.
 */
@Service
public class UserRepositoryImpl implements UserRepository {

    private UserRepository userRepository;


    @Autowired
    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public <S extends User> S insert(S s) {
        return userRepository.insert(s);
    }

    public <S extends User> S save(S s) {
        return userRepository.save(s);
    }

    public <S extends User> Iterable<S> save(Iterable<S> iterable) {
        return userRepository.save(iterable);
    }

    @Override
    public User findOne(MapId mapId) {
        return userRepository.findOne(mapId);
    }

    @Override
    public boolean exists(MapId mapId) {
        return userRepository.exists(mapId);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Iterable<User> findAll(Iterable<MapId> iterable) {
        return userRepository.findAll(iterable);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void delete(MapId mapId) {
        userRepository.delete(mapId);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void delete(Iterable<? extends User> iterable) {
        userRepository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
