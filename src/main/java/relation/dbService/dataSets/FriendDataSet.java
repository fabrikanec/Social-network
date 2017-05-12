package relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "friend")
public class FriendDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final Long serialVersionUID = -8706689714326132798L;

    @Column(name = "user_id")
    private Long id;

    @Column(name = "friend_id")
    private Long friend_id;

    @Id
    @Column(name = "syntetic_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long syntetic_id;

    @ManyToMany(fetch = FetchType.EAGER) // fetch = FetchType.LAZY
    private Set<UsersDataSet> users = new HashSet<>();

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public FriendDataSet() {
    }

    public FriendDataSet(UsersDataSet user, UsersDataSet friend) {
        this.setId(user);
        this.setFriendId(friend);
        this.setFriend(friend);

    }


    @SuppressWarnings("UnusedDeclaration")

    public void setId(UsersDataSet user) { this.id = user.getId(); }

    public Long getFriend() { return friend_id; }

    public void setFriendId(UsersDataSet friend) { this.friend_id = friend.getId(); }

    public Set<UsersDataSet> getFriends() {
        return users;
    }

    public void setFriend(UsersDataSet user) {
        this.users.add(user);
    }


    public String toString() {
        return "FriendDataSet{" +
                "id=" + id +
                ", friend_id='" + friend_id + '\'' +
                '}';
    }
}