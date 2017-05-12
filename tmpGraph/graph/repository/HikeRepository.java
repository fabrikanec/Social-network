package graph.repository;

import graph.entity.Hike;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * {@link Hike} CRUD operations.
 *
 * @author Gunnar Morling
 */
public class HikeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Hike create(Hike hike) {
        if (hike.getOrganizer() != null) {
            hike.getOrganizer().getOrganizedHikes().add(hike);
        }

        entityManager.persist(hike);

        return hike;
    }

    public Hike get(String id) {
        return entityManager.find(Hike.class, id);
    }

    public List<Hike> getAll() {
        return entityManager.createQuery("FROM Hike h", Hike.class).getResultList();
    }

    public void remove(Hike hike) {
        entityManager.remove(hike);
        hike.getOrganizer().getOrganizedHikes().remove(hike);
    }
}