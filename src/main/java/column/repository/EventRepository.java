package main.java.column.repository;

import main.java.column.model.Event;
import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * Created by cezar on 5/23/17.
 */
public interface EventRepository extends CassandraRepository<Event> {
}
