package org.baeldung.spring.data.cassandra.repository;

import org.baeldung.spring.data.cassandra.model.Article;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CassandraRepository<Article> {

    @Query("select * from article where title = ?0 and publisher=?1")
    Iterable<Article> findByTitleAndPublisher(String title, String publisher);

    <S extends Article>Iterable<S> save(Article article);

    Article findOne(MapId mapId);

    boolean exists(MapId mapId);

    Iterable<Article> findAll();

    Iterable<Article> findAll(Iterable<MapId> iterable);

    long count();

    void delete(MapId mapId);

    void delete(Article article);

    void delete(Iterable<? extends Article> iterable);

    void deleteAll();
}
