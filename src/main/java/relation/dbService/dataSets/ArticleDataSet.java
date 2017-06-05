package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "article")
public class ArticleDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "article_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleId;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    public ArticleDataSet(String publisher, String title, String text) {
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

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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