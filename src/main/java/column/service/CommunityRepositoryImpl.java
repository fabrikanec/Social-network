package main.java.column.service;

import main.java.column.model.Community;
import main.java.column.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by cezar on 5/24/17.
 */
@Service
public class CommunityRepositoryImpl implements CommunityRepository {
    
    private CommunityRepository communityRepository;

    @Autowired
    public void setCommunityRepository(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public <S extends Community> S insert(S s) {
        return communityRepository.insert(s);
    }

    public <S extends Community> S save(S s) {
        return communityRepository.save(s);
    }

    public <S extends Community> Iterable<S> save(Iterable<S> iterable) {
        return communityRepository.save(iterable);
    }

    @Override
    public Community findOne(MapId mapId) {
        return communityRepository.findOne(mapId);
    }

    @Override
    public boolean exists(MapId mapId) {
        return communityRepository.exists(mapId);
    }

    @Override
    public Iterable<Community> findAll() {
        return communityRepository.findAll();
    }

    @Override
    public Iterable<Community> findAll(Iterable<MapId> iterable) {
        return communityRepository.findAll(iterable);
    }

    @Override
    public long count() {
        return communityRepository.count();
    }

    @Override
    public void delete(MapId mapId) {
        communityRepository.delete(mapId);
    }

    @Override
    public void delete(Community community) {
        communityRepository.delete(community);
    }

    @Override
    public void delete(Iterable<? extends Community> iterable) {
        communityRepository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        communityRepository.deleteAll();
    }
}
