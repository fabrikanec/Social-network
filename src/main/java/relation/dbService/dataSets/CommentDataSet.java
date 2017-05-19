package relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "comment")
public class CommentDataSet implements Serializable {
    private static final Long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "comment_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column(name = "user_id")
    private Long id;

    @Column (name = "text")
    private String text;


    @SuppressWarnings("UnusedDeclaration")
    public CommentDataSet() {
    }

    public CommentDataSet(Long id, String text) {
        this.setId(id);
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
