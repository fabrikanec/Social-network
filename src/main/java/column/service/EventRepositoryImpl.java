package main.java.column.service;

import main.java.column.model.Event;
import main.java.column.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by cezar on 5/23/17.
 */
@Service
public class EventRepositoryImpl implements EventRepository {

    private EventRepository eventRepository;


    @Autowired
    public void setEventRepository(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public <S extends Event> S insert(S s) {
        return eventRepository.insert(s);
    }

    public <S extends Event> S save(S s) {
        return eventRepository.save(s);
    }

    public <S extends Event> Iterable<S> save(Iterable<S> iterable) {
        return eventRepository.save(iterable);
    }

    @Override
    public Event findOne(MapId mapId) {
        return eventRepository.findOne(mapId);
    }

    @Override
    public boolean exists(MapId mapId) {
        return eventRepository.exists(mapId);
    }

    @Override
    public Iterable<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Iterable<Event> findAll(Iterable<MapId> iterable) {
        return eventRepository.findAll(iterable);
    }

    @Override
    public long count() {
        return eventRepository.count();
    }

    @Override
    public void delete(MapId mapId) {
        eventRepository.delete(mapId);
    }

    @Override
    public void delete(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public void delete(Iterable<? extends Event> iterable) {
        eventRepository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
    }
}
