package main.java.column.repository;

import main.java.column.model.Community;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cezar on 5/24/17.
 */
@Repository
public interface CommunityRepository extends CassandraRepository<Community> {
}
