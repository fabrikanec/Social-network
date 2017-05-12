package graph.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class UserVIP {
    private Long id;

    private String name;

    @Relationship(type="owns")
    private List<User> friends;

    public UserVIP(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> users) {
        this.friends = users;
    }

    public void addFriend(User user) {
        this.friends.add(user);
    }

    public void addFriends(List<User> users) {
        this.friends.addAll(users);
    }

    public void blockFriend(User user) {
        this.friends.remove(user);
    }

    public void blockFriends(List<User> users) {
        this.friends.removeAll(users);
    }

    public void blockAllFriends() {
        this.friends.clear();
    }
}
