package org.baeldung.spring.data.cassandra.model;

import com.datastax.driver.core.DataType.Name;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.MapIdentifiable;
import org.springframework.data.cassandra.repository.support.BasicMapId;

import java.util.*;

@Table(value = "user")
public class User implements MapIdentifiable {

	@PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED,
			ordinal = 0) @CassandraType(type = Name.UUID) private UUID userId;
	@PrimaryKeyColumn(name = "auth_token", type = PrimaryKeyType.CLUSTERED,
			ordinal = 1) @CassandraType(type = Name.UUID) private UUID token;

	private List<Comment> userComments = new ArrayList<>();

	private List<Event> userEvents = new ArrayList<>();

	private List<Article> userArticles = new ArrayList<>();

	private List<Message> userMessages = new ArrayList<>();

	@Column("user_friends")
	private Set<UUID> userFriends = new HashSet<>();

	@Column("password")
	private String password;

	@Column("firstname")
	private String firstName;

	@Column ("lastname")
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
	public MapId getMapId() {
		return BasicMapId.id("firstname", firstName).with("lastname", lastName);
	}
}