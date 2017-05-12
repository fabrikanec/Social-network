package graph.model;
import graph.entity.Person;

import java.net.URI;
import java.util.Set;


/**
 * The {@link Person} representation used during REST calls.
 *
 * @author Davide D'Alto
 */
public class PersonDocument {

    private String firstName;
    private String lastName;
    private Set<URI> organizedHikes;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<URI> getOrganizedHikes() {
        return organizedHikes;
    }

    public void setOrganizedHikes(Set<URI> organizedHikes) {
        this.organizedHikes = organizedHikes;
    }
}