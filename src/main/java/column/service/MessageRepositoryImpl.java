package main.java.column.service;

import main.java.column.repository.MessageRepository;
import main.java.column.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by cezar on 5/23/17.
 */
@Service
public class MessageRepositoryImpl implements MessageRepository {

    private MessageRepository messageRepository;


    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public <S extends Message> S insert(S s) {
        return messageRepository.insert(s);
    }

    public <S extends Message> S save(S s) {
        return messageRepository.save(s);
    }

    public <S extends Message> Iterable<S> save(Iterable<S> iterable) {
        return messageRepository.save(iterable);
    }

    @Override
    public Message findOne(MapId mapId) {
        return messageRepository.findOne(mapId);
    }

    @Override
    public boolean exists(MapId mapId) {
        return messageRepository.exists(mapId);
    }

    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Iterable<Message> findAll(Iterable<MapId> iterable) {
        return messageRepository.findAll(iterable);
    }

    @Override
    public long count() {
        return messageRepository.count();
    }

    @Override
    public void delete(MapId mapId) {
        messageRepository.delete(mapId);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public void delete(Iterable<? extends Message> iterable) {
        messageRepository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        messageRepository.deleteAll();
    }
}
