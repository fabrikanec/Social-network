package graph.resources;

import graph.entity.Person;
import graph.mapper.ResourceMapper;
import graph.model.PersonDocument;
import graph.repository.PersonRepository;

import java.util.List;

/**
 * REST resource for managing {@link Person}s.
 *
 * @author Gunnar Morling
 */

public class Persons {

    private PersonRepository personRepository;

    private ResourceMapper mapper;

    public List<PersonDocument> listPersons() {
        List<Person> persons = personRepository.getAll();
        return mapper.toPersonDocuments(persons);
    }


    public PersonDocument createPerson(PersonDocument request) {
        Person person = personRepository.create(mapper.toPerson(request));
        return mapper.toPersonDocument(person);
    }


    public PersonDocument getPerson(String id) {
        Person person = personRepository.get(id);
        if (person == null) {
            return null; // TODO: 5/8/17
        }
        else {
            return mapper.toPersonDocument(person);
        }
    }


    public void updatePerson(PersonDocument request, String id) {
        Person person = personRepository.get(id);
        if (person == null) {
            return; // TODO: 5/8/17
        }

        mapper.updatePerson(request, person);
    }

    public void deletePerson(String id) {
        personRepository.remove(new Person(id));
    }
}