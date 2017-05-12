package graph.repostory;

import graph.domain.Video;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface VideoRepository extends GraphRepository<Video> {

    Video findByTitle(@Param("title") String title);

    @Query("MATCH (m:Photo) WHERE m.title =~ ('(?i).*'+{title}+'.*') RETURN m")
    Collection<Video> findByTitleContaining(@Param("title") String title);

    @Query("MATCH (v:Video)<-[:ACTED_IN]-(a:User) RETURN v.title as video, collect(a.name) as cast LIMIT {limit}")
    List<Map<String, Object>> graph(@Param("limit") int limit);
}
