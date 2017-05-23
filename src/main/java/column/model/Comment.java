package main.java.column.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.UUID;

@UserDefinedType
@Persistent
public class Comment {

    @Id
    private UUID comment_id;

    private UUID user_id;

    private UUID article_id;

    private UUID event_id;

    private String text;

    public Comment(UUID comment_id, UUID user_id, UUID article_id, UUID event_id, String text) {
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.article_id = article_id;
        this.event_id = event_id;
        this.text = text;
    }

    public UUID getComment_id() {
        return comment_id;
    }

    public void setComment_id(UUID comment_id) {
        this.comment_id = comment_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public UUID getArticle_id() {
        return article_id;
    }

    public void setArticle_id(UUID article_id) {
        this.article_id = article_id;
    }

    public UUID getEvent_id() {
        return event_id;
    }

    public void setEvent_id(UUID event_id) {
        this.event_id = event_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}