package graph.repository;

import graph.entity.Hike;
import graph.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * {@link Person} CRUD operations.
 *
 * @author Gunnar Morling
 */
public class PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Person create(Person person) {
        entityManager.persist(person);
        return person;
    }

    public Person get(String id) {
        return entityManager.find(Person.class, id);
    }

    public List<Person> getAll() {
        return entityManager.createQuery("FROM Person p", Person.class).getResultList();
    }

    public Person save(Person person) {
        return entityManager.merge(person);
    }

    public void remove(Person person) {
        entityManager.remove(person);
        for (Hike hike : person.getOrganizedHikes()) {
            hike.setOrganizer(null);
        }
    }
}