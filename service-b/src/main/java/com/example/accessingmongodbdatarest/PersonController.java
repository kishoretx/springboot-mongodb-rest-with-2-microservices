package com.example.accessingmongodbdatarest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> getAllPerson() {
        System.out.println("GET ALL PERSONS");
        return personRepository.findAll();
    }

    @GetMapping("/count")
    public long getCount() {
        System.out.println("GET ALL PERSONS - COUNT v4");
        return personRepository.count();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        System.out.println("----- Calling  Another MS- POST");
        return personRepository.save(person);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable String id) {
        System.out.println("----- Calling  Another MS- GET");
        return personRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable String id) {
        System.out.println("----- Calling  Another MS- DELETE");
        personRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person updatedPerson) {
        System.out.println("----- Calling  Another MS- PUT");
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setGender(updatedPerson.getGender());

            person.setAddresses(updatedPerson.getAddresses());
            // Update other fields as necessary

            personRepository.save(person);
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> patchPerson(@PathVariable String id, @RequestBody Person partialPerson) {
        System.out.println("----- Calling  Another MS- PATCH");
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            // Update fields if they are present in the partialPerson
            if (partialPerson.getFirstName() != null) {
                person.setFirstName(partialPerson.getFirstName());
            }
            if (partialPerson.getLastName() != null) {
                person.setLastName(partialPerson.getLastName());
            }
            if (partialPerson.getGender() != null) {
                person.setGender(partialPerson.getGender());
            }
            if (partialPerson.getAddresses() != null) {
                person.setAddresses(partialPerson.getAddresses());
            }

            // Save updated person
            personRepository.save(person);
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
