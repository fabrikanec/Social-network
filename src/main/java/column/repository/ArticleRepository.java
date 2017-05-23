package main.java.column.repository;

import main.java.column.model.Article;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CassandraRepository<Article> {

    /*@Query("select * from article where title = ?0 and publisher=?1")
    Iterable<Article> findByTitleAndPublisher(String title, String publisher);

    @Query("select * from article where article_id = ?0")
    Article findSingle(MapId mapId);

    boolean exists(MapId mapId);

//    @Query("select * from article where title = ?0")
//    Iterable<Article> findAll();

    @Query("select * from article where title = ?0")
    Iterable<Article> findAllArticles(String title);

    long count();

    void delete(MapId mapId);

    void delete(Article article);

    void delete(Iterable<? extends Article> iterable);

    void deleteAll();

    <S extends Article> S insert(S s);

    <S extends Article> S save(S s);

    <S extends Article> Iterable<S> save(Iterable<S> iterable);

//    @Query("select * from article where ") //TODO
//    Iterable<Article> findAll(Iterable<MapId> iterable);
*/

    //VUUUUUUUUUUUUUUUUUUUUUUUUUUU



}
