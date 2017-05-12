package graph.resources;

import graph.entity.Hike;
import graph.mapper.ResourceMapper;
import graph.model.HikeDocument;
import graph.repository.HikeRepository;

import java.util.List;


/**
 * REST resource for managing {@link Hike}s.
 *
 * @author Gunnar Morling
 */

public class Hikes {

    private HikeRepository hikeRepository;
    private ResourceMapper mapper;

    public Hikes() {
    }

    public List<HikeDocument> listHikes() { // TODO: 5/8/17
        List<Hike> hikes = hikeRepository.getAll();

        return mapper.toHikeDocuments(hikes);
    }

    public HikeDocument createHike(HikeDocument request) throws Exception {
        Hike hike = hikeRepository.create(mapper.toHike(request));
        return mapper.toHikeDocument(hike);
    }

    public HikeDocument getHike(String id) {
        Hike hike = hikeRepository.get(id);
        if (hike == null) {
            return null; // TODO: 5/8/17
            //return Response.status( Status.NOT_FOUND ).build();
        } else {
            return mapper.toHikeDocument(hike);
        }
    }

    public void updateHike(HikeDocument request, String id) {
        Hike hike = hikeRepository.get(id);
        if (hike == null) {
            //return Response.status( Status.NOT_FOUND ).build();
            return;
        }

        mapper.updateHike(request, hike);

        if (hike.getOrganizer() != null) {
            hike.getOrganizer().getOrganizedHikes().add(hike);
        }
    }

    public void deletePerson( String id) {
        hikeRepository.remove(new Hike(id));
    }
}