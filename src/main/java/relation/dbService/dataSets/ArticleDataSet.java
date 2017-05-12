package relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "article")
public class ArticleDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final Long serialVersionUID = -8706689714326132798L;

    @Column(name = "user_id")
    private Long id;

    @Id
    @Column(name = "article_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long article_id;

    @Column(name = "secure")
    private char secure;

    @Column (name = "text")
    private String text;

    @Column (name = "date")
    private Date date;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public ArticleDataSet() {
    }

    public ArticleDataSet(Long id, char secure, String text, Date date) {
        this.setId(id);
        //this.setArticleId(article_id);
        this.setSecure(secure);
        this.setText(text);
        this.setDate(date);
    }


    @SuppressWarnings("UnusedDeclaration")

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return article_id;
    }

    public void setArticleId(Long article_id) {
        this.article_id = article_id;
    }

    public char getSecure() { return secure; }

    public void setSecure(char flag) { this.secure = flag; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String toString() {
        return "ArticleDataSet{" +
                "id=" + id +
                ", article_id='" + article_id + '\'' +
                '}';
    }
}