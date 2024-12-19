package com.example.GraphQL.controllers;

import com.example.GraphQL.data.DataLoader;
import com.example.GraphQL.records.Address;
import com.example.GraphQL.records.Person;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PersonController {
    private final DataLoader dataLoader;

    public PersonController(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @QueryMapping
    public List<Person> getAllPersons() {
        return dataLoader.getPersons();
    }

    @QueryMapping
    public Person getPersonByName(@Argument String name) {
        return dataLoader.getPersonByName(name);
    }

    @QueryMapping
    public Person getPersonByEmail(@Argument String email) {
        return dataLoader.getPersons().stream()
                .filter(person -> person.email().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @QueryMapping
    public List<Address> getAllAddresses() {
        return dataLoader.getPersons().stream()
                .map(Person::adresse)
                .collect(Collectors.toList());
    }

    @MutationMapping
    public Person addPerson(
            @Argument String nomPrenom,
            @Argument String telephone,
            @Argument String email,
            @Argument Address adresse) {
        return new Person(nomPrenom, telephone, email, adresse);
    }

    @MutationMapping
    public Person updatePerson(
            @Argument String email,
            @Argument String nomPrenom,
            @Argument String telephone,
            @Argument Address adresse) {
        Person person = dataLoader.getPersons().stream()
                .filter(p -> p.email().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (person != null) {
            person = new Person(nomPrenom, telephone, email, adresse);
            return person;
        }

        return null;
    }

    @MutationMapping
    public boolean deletePerson(@Argument String email) {
        List<Person> persons = dataLoader.getPersons();
        Person person = persons.stream()
                .filter(p -> p.email().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (person != null) {
            persons.remove(person);
            return true;
        }
        return false;
    }


    @QueryMapping
    public List<Person> getPersonsByNamePattern(@Argument String pattern) {
        return dataLoader.getPersons().stream()
                .filter(person -> person.nomPrenom().contains(pattern))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<Person> getAllPersonsSortedByName() {
        return dataLoader.getPersons().stream()
                .sorted(Comparator.comparing(Person::nomPrenom))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<Person> searchPersons(@Argument String nomPrenom, @Argument String email) {
        return dataLoader.getPersons().stream()
                .filter(person -> person.nomPrenom().contains(nomPrenom) && person.email().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }



    @SchemaMapping
    public Address adresse(Person person) {
        return person.adresse();
    }
}
