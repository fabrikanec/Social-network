package graph.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field(analyze = Analyze.NO)
    private String firstName;

    @Field(analyze = Analyze.NO)
    private String lastName;

    @ManyToMany
    private Set<Friend> friends;

    public User() {

    }

    public User(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Set<Friend> getFriends() {
        return friends;
    }

    public void setFriends(final Set<Friend> friends) {
        this.friends = friends;
    }

    public boolean addItem(final Friend friend) {
        if (this.friends == null) {
            this.friends = new HashSet<>();
        }
        return this.friends.add(friend);
    }

    public boolean removeItem(final Friend friend) {
        return this.friends.remove(friend);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}