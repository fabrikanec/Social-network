package graph.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@JsonIdentityInfo(generator = JSOGGenerator.class)
@NodeEntity
public class User {
    @GraphId
    Long id;
    
    @Relationship(type = "ACTED_IN")
    private List<User> friends;

    private String login;

    private String password;

    private String name;

    private String secondName;

    public User() {
    }

}