package graph.mapper;

import graph.entity.Hike;
import graph.entity.Person;
import graph.model.HikeDocument;
import graph.model.PersonDocument;

import java.util.List;

/**
 * A MapStruct-generated mapper for hikes and persons.
 * <p>
 * Contains the methods fo rthe conversion between {@link PersonDocument} and {@link Person} or {@link Hike} and
 * {@link HikeDocument}. It also contains the methods to update a {@link Person} or {@link Hike} using the values in a
 * {@link PersonDocument} or {@link HikeDocument}.
 *
 * @author Gunnar Morling
 */

public interface ResourceMapper {

    PersonDocument toPersonDocument(Person person);

    Person toPerson(PersonDocument personDocument);

    void updatePerson(PersonDocument personDocument, Person person);

    List<PersonDocument> toPersonDocuments(Iterable<Person> persons);

    HikeDocument toHikeDocument(Hike hike);

    Hike toHike(HikeDocument hikeDocument);

    void updateHike(HikeDocument hikeDocument, Hike hike);

    List<HikeDocument> toHikeDocuments(Iterable<Hike> hikes);
}