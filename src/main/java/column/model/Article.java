package main.java.column.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@UserDefinedType
@Persistent
public class Article {

    @Id
    private UUID id;

    private String title;

    private String publisher;

    private Set<String> tags = new HashSet<>();

    public Article(UUID id, String title, String publisher, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.tags = tags;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setTag(String tag) {
        this.tags.add(tag);
    }

}
