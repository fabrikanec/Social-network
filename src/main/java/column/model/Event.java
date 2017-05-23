package main.java.column.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.UUID;

/**
 * Created by cezar on 5/18/17.
 */
@UserDefinedType
@Persistent
public class Event {

    @Id
    private UUID event_id;

    private UUID user_id;

    private String name;

    private String text;

    private String subj;

    public Event(UUID event_id, UUID user_id, String name, String text, String subj) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.name = name;
        this.text = text;
        this.subj = subj;
    }

    public UUID getEvent_id() {
        return event_id;
    }

    public void setEvent_id(UUID event_id) {
        this.event_id = event_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubj() {
        return subj;
    }

    public void setSubj(String subj) {
        this.subj = subj;
    }
}