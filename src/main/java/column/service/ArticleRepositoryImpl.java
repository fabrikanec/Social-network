package main.java.column.service;

import main.java.column.model.Article;
import main.java.column.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by cezar on 5/23/17.
 */
@Service
public class ArticleRepositoryImpl implements ArticleRepository {

    private ArticleRepository articleRepository;


    @Autowired
    public void setArticleRepository(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public <S extends Article> S insert(S s) {
        return articleRepository.insert(s);
    }

    public <S extends Article> S save(S s) {
        return articleRepository.save(s);
    }

    public <S extends Article> Iterable<S> save(Iterable<S> iterable) {
        return articleRepository.save(iterable);
    }

    @Override
    public Article findOne(MapId mapId) {
        return articleRepository.findOne(mapId);
    }

    @Override
    public boolean exists(MapId mapId) {
        return articleRepository.exists(mapId);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Iterable<Article> findAll(Iterable<MapId> iterable) {
        return articleRepository.findAll(iterable);
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    @Override
    public void delete(MapId mapId) {
        articleRepository.delete(mapId);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public void delete(Iterable<? extends Article> iterable) {
        articleRepository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        articleRepository.deleteAll();
    }
}
