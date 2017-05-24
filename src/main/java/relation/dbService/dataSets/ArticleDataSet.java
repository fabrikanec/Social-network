package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "article")
public class ArticleDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "article_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long article_id;
    @Column(name = "publisher")
    private String publisher;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    public ArticleDataSet(Long article_id, String publisher, String title, String text) {
        this.article_id = article_id;
        this.publisher = publisher;
        this.title = title;
        this.text = text;
    }

    public ArticleDataSet() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}