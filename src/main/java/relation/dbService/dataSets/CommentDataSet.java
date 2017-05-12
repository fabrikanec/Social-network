package relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "comment")
public class CommentDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final Long serialVersionUID = -8706689714326132798L;

    @Column(name = "user_id")
    private Long id;

    @Id
    @Column(name = "comment_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column(name = "article_id")
    private Long article_id;

    @Column (name = "event_id")
    private Long event_id;

    @Column (name = "text")
    private String text;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public CommentDataSet() {
    }

    public CommentDataSet(Long id, Long article_id, Long event_id, String text) {
        this.setId(id);
        //this.setCommentId(comment_id);
        this.setArticleId(article_id);
        this.setEventId(event_id);
        this.setText(text);
    }

    @SuppressWarnings("UnusedDeclaration")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return comment_id;
    }

    public void setCommentId(Long comment_id) {
        this.comment_id = comment_id;
    }

    public Long getArticleId() {
        return article_id;
    }

    public void setArticleId(Long article_id) { this.article_id = article_id; }

    public Long getEventId() {
        return event_id;
    }

    public void setEventId(Long event_id) {
        this.event_id = event_id;
    }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }



    @Override
    public String toString() {
        return "CommentDataSet{" +
                "id=" + id +
                ", comment_id='" + comment_id + '\'' +
                '}';
    }
}
