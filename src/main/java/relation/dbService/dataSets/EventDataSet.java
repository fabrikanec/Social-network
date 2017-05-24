package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "event")
public class EventDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "event_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;

    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column (name = "text")
    private String text;

    @Column (name = "subj")
    private String subj;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public EventDataSet() {
    }

    public EventDataSet(Long id, String name, String text, String subj) {
        this.setId(id);
        //this.setEventId(event_id);
        this.setName(name);
        this.setText(text);
        this.setSubj(subj);
    }


    @SuppressWarnings("UnusedDeclaration")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return event_id;
    }

    public void setEventId(Long event_id) { this.event_id = event_id; }

    public String getName() { return name; }

    public void setName(String  name) { this.name = name; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public String getSubj() { return subj; }

    public void setSubj(String subj) { this.subj = subj; }

    public String toString() {
        return "EventDataSet{" +
                "id=" + id +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}