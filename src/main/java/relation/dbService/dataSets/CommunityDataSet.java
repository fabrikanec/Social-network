package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "community")
public class CommunityDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "community_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long community_id;

    @Column(name = "community_name")
    private String community_name;

    @ManyToMany(fetch = FetchType.EAGER) // fetch = FetchType.LAZY
    //@JoinTable(name = "users_communities", joinColumns = @JoinColumn(name = "community_name"),
            //inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDataSet> users = new HashSet<>();

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public CommunityDataSet() {
    }

    public CommunityDataSet(String community_name) {
        //this.setId(id);
        //this.setCommunityId(community_id);
        this.setCommunityName(community_name);
    }

    public CommunityDataSet(UserDataSet user, String community_name) {
        this.setUser(user);
        //this.setCommunityId(community_id);
        this.setCommunityName(community_name);
    }

    public Long getCommunityId() {
        return community_id;
    }

    public void setCommunityId(Long community_id) {
        this.community_id = community_id;
    }

    public String getCommunityName() { return community_name;}

    public void setCommunityName(String name) { this.community_name = name;}

    public Set<UserDataSet> getUsers() {
        return users;
    }

    //public void setUsers(Set<Long> users) { this.users = users; }

    public void setUser(UserDataSet user) {
        this.users.add(user);
    }

    public String toString() {
        return "CommunityDataSet{" +
                ", community_id='" + community_id + '\'' +
                '}';
    }
}