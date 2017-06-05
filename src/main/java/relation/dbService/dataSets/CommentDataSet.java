package main.java.relation.dbService.dataSets;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "comment")
public class CommentDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "comment_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "article_id")
    private Long articleId;

    @Column (name = "event_id")
    private Long eventId;

    @Column (name = "text")
    private String text;

    public CommentDataSet() {
    }

    public CommentDataSet(Long userId, Long articleId, Long eventId, String text) {
        this.commentId = commentId;
        this.userId = userId;
        this.articleId = articleId;
        this.eventId = eventId;
        this.text = text;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
                "user_id=" + userId +
                ", comment_id='" + commentId + '\'' +
                '}';
    }
}