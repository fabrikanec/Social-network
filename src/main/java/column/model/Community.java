package main.java.column.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by cezar on 5/24/17.
 */
@UserDefinedType
@Persistent
public class Community {
    @Id
    private UUID community_id;

    private String community_name;

    private Set<UUID> users = new HashSet<>();

    public Community(UUID community_id, String community_name, Set<UUID> users) {
        this.community_id = community_id;
        this.community_name = community_name;
        this.users = users;
    }

    public UUID getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(UUID community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public Set<UUID> getUsers() {
        return users;
    }

    public void setUsers(Set<UUID> users) {
        this.users = users;
    }

    public void addUser(UUID user_id) {
        this.users.add(user_id);
    }

    public void removeUser(UUID user_id) {
        this.users.remove(user_id);
    }

    public void removeUsers() {
        this.users.clear();
    }
}
