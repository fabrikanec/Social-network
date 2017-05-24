package main.java.relation.dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class UserDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final Long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = 0L;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "password", updatable = false)
    private String password;

    @Column (name = "name")
    private String name;

    @Column (name = "surname")
    private String surname;

    @ManyToMany(targetEntity = CommunityDataSet.class, mappedBy="users") //, fetch = FetchType.LAZY
    private Set<Long> communities = new HashSet<>();

    @ManyToMany(targetEntity = CommunityDataSet.class, mappedBy="users")
    private Set<Long> friends = new HashSet<>();

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public UserDataSet() {
    }

    public UserDataSet(Long id, String login, String password, String name, String surname) {
        //this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setName(name);
        this.setSurname(surname);
    }

    @SuppressWarnings("UnusedDeclaration")
    public UserDataSet(Long id, String login, String password) {
        //this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setName(login);
        this.setSurname("");
    }

    public UserDataSet(String login, String password) {
        //this.setId(++this.id);
        this.setLogin(login);
        this.setPassword(password);
        this.setName(login);
        this.setSurname("");
    }

    @SuppressWarnings("UnusedDeclaration")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) { this.surname = surname; }

    public Set<Long> getCommunities() {
        return communities;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public void setCommunities(Set<Long> communities) {
        this.communities = communities;
    }

    public void setFriends(Set<Long> friends) {
        this.friends = friends;
    }

    public void setCommunity(Long community_id) {
        this.communities.add(community_id);
    }

    public void setFriend(Long friend_id) {
        this.friends.add(friend_id);
    }


    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}