package graph.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.Collection;

@JsonIdentityInfo(generator = JSOGGenerator.class)
@RelationshipEntity(type = "ACTED_IN")
public class Role {
    @GraphId
    Long id;
    private Collection<String> roles;
    @StartNode
    private User user;
    @EndNode
    private Video video;

    public Role() {
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public User getUser() {
        return user;
    }

    public Video getVideo() {
        return video;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
