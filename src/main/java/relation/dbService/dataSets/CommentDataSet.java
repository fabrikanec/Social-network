package main.java.relation.dbService.dataSets;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "comment")
public class CommentDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "comment_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column(name = "user_id")
    private Long id;

    @Column(name = "article_id")
    private Long article_id;

    @Column (name = "event_id")
    private Long event_id;

    @Column (name = "text")
    private String text;

    public CommentDataSet() {
    }

    public CommentDataSet(Long comment_id, Long id, Long article_id, Long event_id, String text) {
        this.comment_id = comment_id;
        this.id = id;
        this.article_id = article_id;
        this.event_id = event_id;
        this.text = text;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CommentDataSet{" +
                "id=" + id +
                ", comment_id='" + comment_id + '\'' +
                '}';
    }
}