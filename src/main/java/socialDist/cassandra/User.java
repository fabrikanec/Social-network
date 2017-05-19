package socialDist.cassandra;


import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.*;

/*
CREATE TABLE airport.flights (
    dest_airport uuid,
    dep_airport uuid,
    departure timestamp,
    arrival timestamp,
    aircraft uuid,
    id uuid,
    luggage set<uuid>,
    passengers set<uuid>,
    PRIMARY KEY ((dest_airport, dep_airport), departure, arrival)
 */


@Table(keyspace = "sdb", name = "users",
        readConsistency = "QUORUM",
        writeConsistency = "QUORUM",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
public class User {

    @PartitionKey(0)
    @Column(name = "user_id")
    private UUID userId;

    @ClusteringColumn(0)
    @Column(name = "token")
    private UUID token;

    private List<Comment> userComments = new ArrayList<>();

    private List<Event> userEvents = new ArrayList<>();

    private List<Article> userArticles = new ArrayList<>();

    private List<Message> userMessages = new ArrayList<>();

    @Column(name = "user_friends")
    private Set<UUID> userFriends = new HashSet<>();

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstName;

    @Column (name = "lastname")
    private String lastName;

    public User() {

    }

    public UUID getUserId() {
        return userId;
    }

    public User setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getToken() {
        return token;
    }

    public User setToken(UUID token) {
        this.token = token;
        return this;
    }

    public List<Comment> getUserComments() {
        return userComments;
    }

    public User setUserComments(List<Comment> userComments) {
        this.userComments = userComments;
        return this;
    }

    public List<Event> getUserEvents() {
        return userEvents;
    }

    public User setUserEvents(List<Event> userEvents) {
        this.userEvents = userEvents;
        return this;
    }

    public List<Article> getUserArticles() {
        return userArticles;
    }

    public User setUserArticles(List<Article> userArticles) {
        this.userArticles = userArticles;
        return this;
    }

    public List<Message> getUserMessages() {
        return userMessages;
    }

    public User setUserMessages(List<Message> userMessages) {
        this.userMessages = userMessages;
        return this;
    }

    public Set<UUID> getUserFriends() {
        return userFriends;
    }

    public User setUserFriends(Set<UUID> userFriends) {
        this.userFriends = userFriends;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", token=" + token +
                ", userComments=" + userComments +
                ", userEvents=" + userEvents +
                ", userArticles=" + userArticles +
                ", userMessages=" + userMessages +
                ", userFriends=" + userFriends +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}