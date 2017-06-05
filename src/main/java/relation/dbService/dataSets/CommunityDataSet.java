package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "community")
public class CommunityDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "community_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long communityId;

    @Column(name = "community_name")
    private String communityName;

    @ElementCollection( targetClass = Long.class )
    //@ManyToMany(fetch = FetchType.EAGER) // fetch = FetchType.LAZY
    //@JoinTable(name = "users_communities", joinColumns = @JoinColumn(name = "community_name"),
            //inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Long> users = new HashSet<>();

    public CommunityDataSet() {
    }

    public CommunityDataSet(String communityName) {
        //this.setId(id);
        //this.setCommunityId(communityId);
        this.setCommunityName(communityName);
    }

    public CommunityDataSet(Long user, String communityName) {
        this.users.add(user);
        //this.setCommunityId(communityId);
        this.communityName = communityName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() { return communityName;}

    public void setCommunityName(String name) { this.communityName = name;}

    public Set<Long> getUsers() {
        return users;
    }

    //public void setUsers(Set<Long> users) { this.users = users; }

    public void setUser(Long user_id) {
        this.users.add(user_id);
    }

    public String toString() {
        return "CommunityDataSet{" +
                ", community_id='" + communityId + '\'' +
                '}';
    }
}