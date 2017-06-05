package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "friend") //TODO rearch
public class FriendDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "syntetic_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long synteticId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "friend_id")
    private Long friendId;

    @ElementCollection( targetClass = Long.class )
    //@ManyToMany(fetch = FetchType.EAGER) // fetch = FetchType.LAZY
    private Set<Long> users = new HashSet<>();

    public FriendDataSet() {
    }

    public FriendDataSet(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
        this.users.add(friendId);

    }


    public void setId(Long userId) { this.userId = userId; }

    public Long getFriend() { return friendId; }

    public void setFriendId(UserDataSet friend) { this.friendId = friend.getId(); }

    public Set<Long> getFriends() {
        return users;
    }

    public void setFriend(Long userId) {
        this.users.add(userId);
    }


    public String toString() {
        return "FriendDataSet{" +
                "id=" + userId +
                ", friend_id='" + friendId + '\'' +
                '}';
    }
}