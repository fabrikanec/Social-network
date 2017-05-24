package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "friend")
public class FriendDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "syntetic_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long syntetic_id;

    @Column(name = "user_id")
    private Long id;

    @Column(name = "friend_id")
    private Long friend_id;

    @ManyToMany(fetch = FetchType.EAGER) // fetch = FetchType.LAZY
    private Set<UserDataSet> users = new HashSet<>();

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public FriendDataSet() {
    }

    public FriendDataSet(UserDataSet user, UserDataSet friend) {
        this.setId(user);
        this.setFriendId(friend);
        this.setFriend(friend);

    }


    @SuppressWarnings("UnusedDeclaration")

    public void setId(UserDataSet user) { this.id = user.getId(); }

    public Long getFriend() { return friend_id; }

    public void setFriendId(UserDataSet friend) { this.friend_id = friend.getId(); }

    public Set<UserDataSet> getFriends() {
        return users;
    }

    public void setFriend(UserDataSet user) {
        this.users.add(user);
    }


    public String toString() {
        return "FriendDataSet{" +
                "id=" + id +
                ", friend_id='" + friend_id + '\'' +
                '}';
    }
}