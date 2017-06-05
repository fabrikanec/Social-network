package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "event")
public class EventDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "event_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column (name = "text")
    private String text;

    @Column (name = "subj")
    private String subj;

    public EventDataSet() {
    }

    public EventDataSet(Long userId, String name, String text, String subj) {
        this.setId(userId);
        //this.setEventId(eventId);
        this.setName(name);
        this.setText(text);
        this.setSubj(subj);
    }


    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getName() { return name; }

    public void setName(String  name) { this.name = name; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public String getSubj() { return subj; }

    public void setSubj(String subj) { this.subj = subj; }

    public String toString() {
        return "EventDataSet{" +
                "id=" + userId +
                ", event_id='" + eventId + '\'' +
                '}';
    }
}