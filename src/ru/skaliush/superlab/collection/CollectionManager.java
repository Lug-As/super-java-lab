package ru.skaliush.superlab.collection;

import ru.skaliush.superlab.models.Person;
import ru.skaliush.superlab.models.dto.PersonDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class CollectionManager {
    private final Collection<Person> collection = new HashSet<>();

    private long lastId = 0;

    public Collection<Person> getCollection() {
        return new HashSet<>(collection);
    }

    public void addPerson(PersonDTO personDTO) {
        Person person = new Person(generateId(), personDTO);
        collection.add(person);
    }

    public void updatePersonById(Long id, PersonDTO personDTO) {
        removePersonById(id);
        Person person = new Person(id, personDTO);
        collection.add(person);
    }

    public void removePersonById(Long id) {
        collection.removeIf(person -> person.getId().equals(id));
    }

    public void clearCollection() {
        collection.clear();
    }

    public void addPersonIfMin(Person person) {
        Person minPerson = Collections.min(collection);
        if (person.compareTo(minPerson) < 0) {
            collection.add(person);
        }
    }

    public void removeGreaterThanHeight(int height) {
        collection.removeIf(person -> person.getHeight() > height);
    }

    public void removeLowerThanHeight(int height) {
        collection.removeIf(person -> person.getHeight() < height);
    }

    public String getCollectionType() {
        return getCollection().getClass().getSimpleName();
    }

    private Long generateId() {
        return ++lastId;
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }
}
