package com.example.GraphQL.data;

import com.example.GraphQL.records.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader {
    private final List<Person> persons = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        try {
            // Load the JSON file from the resources directory
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/data.json");

            if (inputStream == null) {
                throw new FileNotFoundException("data.json file not found in resources directory");
            }

            TypeReference<List<Person>> typeReference = new TypeReference<>() {};
            List<Person> loadedPersons = objectMapper.readValue(inputStream, typeReference);

            persons.addAll(loadedPersons);
            System.out.println("Data successfully loaded from data.json");
        } catch (Exception e) {
            // Log the error and rethrow it to prevent the application from starting with incorrect data
            System.err.println("Failed to load data from data.json: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Person getPersonByName(String name) {
        return persons.stream()
                .filter(person -> person.nomPrenom().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

