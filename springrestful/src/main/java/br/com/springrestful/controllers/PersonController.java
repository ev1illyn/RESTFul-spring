package br.com.springrestful.controllers;

import br.com.springrestful.models.Person;
import br.com.springrestful.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll(){
        return personServices.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable(value= "id") Long id){
        return personServices.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person){
        return personServices.create(person);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person){
        return personServices.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value= "id") Long id){

        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
