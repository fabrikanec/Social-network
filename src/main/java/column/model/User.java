package main.java.column.model;

import com.datastax.driver.core.DataType.Name;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.*;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.MapIdentifiable;
import org.springframework.data.cassandra.repository.support.BasicMapId;

import java.util.*;

@Table(value = "users")
public class User implements MapIdentifiable {

	@PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED,
			ordinal = 0) @CassandraType(type = Name.UUID) private UUID userId;

	@PrimaryKeyColumn(name = "auth_token", type = PrimaryKeyType.CLUSTERED,
			ordinal = 1) @CassandraType(type = Name.UUID) private UUID token;

	private Set<Comment> userComments = new HashSet<>();

	private Set<Event> userEvents = new HashSet<>();

	private Set<Article> userArticles = new HashSet<>();

	private Set<Message> userMessages = new HashSet<>();
	
	private Set<Community> userCommunities = new HashSet<>();

	@Column("user_friends")
	private Set<UUID> userFriends = new HashSet<>();

	@Column("password")
	private String password;

	@Column("firstname")
	private String firstName;

	@Column ("lastname")
	private String lastName;

	public User(UUID userId, UUID token, Set<Comment> userComments, Set<Event> userEvents, Set<Article> userArticles, Set<Message> userMessages, Set<UUID> userFriends, String password, String firstName, String lastName) {
		this.userId = userId;
		this.token = token;
		this.userComments = userComments;
		this.userEvents = userEvents;
		this.userArticles = userArticles;
		this.userMessages = userMessages;
		this.userFriends = userFriends;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(UUID userId, UUID token) {
		this.userId = userId;
		this.token = token;
	}

	public User(UUID userId, UUID token,  String password, String firstName, String lastName) {
		this.userId = userId;
		this.token = token;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public Set<Comment> getUserComments() {
		return userComments;
	}

	public User setUserComments(Set<Comment> userComments) {
		this.userComments = userComments;
		return this;
	}

	public Set<Event> getUserEvents() {
		return userEvents;
	}

	public User setUserEvents(Set<Event> userEvents) {
		this.userEvents = userEvents;
		return this;
	}

	public Set<Article> getUserArticles() {
		return userArticles;
	}

	public User setUserArticles(Set<Article> userArticles) {
		this.userArticles = userArticles;
		return this;
	}

	public Set<Message> getUserMessages() {
		return userMessages;
	}

	public User setUserMessages(Set<Message> userMessages) {
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

	public Set<Community> getUserCommunities() {
		return userCommunities;
	}

	public void addCommunity(Community community) {
		userCommunities.add(community);
	}

	public void addFriend(UUID friend) {
		userFriends.add(friend);
	}

	public void addMessage(Message message) {
		userMessages.add(message);
	}

	public void addArticle(Article article) {
		userArticles.add(article);
	}

	public void addComment(Comment comment) {
		userComments.add(comment);
	}

	public void addEvent(Event event) {
		userEvents.add(event);
	}

	public void setUserCommunities(Set<Community> userCommunities) {
		this.userCommunities = userCommunities;
	}

	@Override
	public MapId getMapId() {
		return BasicMapId.id("firstname", firstName).with("lastname", lastName);
	}
}