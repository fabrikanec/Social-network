package main.java.graph.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class User {

	@Index
	@GraphId
	private Long id;

	@Index(unique = true)
	private String login;

	private String name;

	@Relationship(type = "COMMUNITY")
	private Set<Community> communities = new HashSet<>();

	@Relationship(type="FRIENDS", direction = Relationship.UNDIRECTED)
	private Set<User> friends = new HashSet<>();

	public User() {
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setCommunities(Set<Community> communities) {
		this.communities = communities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void addFriend(User friend) {
		friends.add(friend);
	}

	public Set<Community> getCommunities() {
		return communities;
	}

	public void addCommunity(Community community) {
		communities.add(community);
	}

}
