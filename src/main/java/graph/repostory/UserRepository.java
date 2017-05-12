package graph.repostory;

import graph.domain.User;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GraphRepository<User> {

    /*Photo findByTitle(@Param("title") String title);

    @Query("MATCH (m:Photo) WHERE m.title =~ ('(?i).*'+{title}+'.*') RETURN m")
    Collection<Photo> findByTitleContaining(@Param("title") String title);

    @Query("MATCH (m:Photo)<-[:ACTED_IN]-(a:User) RETURN m.title as video, collect(a.name) as cast LIMIT {limit}")
    List<Map<String, Object>> graph(@Param("limit") int limit);*/
}
