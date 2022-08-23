package br.com.springrestful.controllers;

import br.com.springrestful.exceptions.UnsupportedMathOperationException;
import br.com.springrestful.model.Person;
import br.com.springrestful.services.PersonServices;
import br.com.springrestful.utils.MathOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static br.com.springrestful.utils.MathCalc.convertToDouble;
import static br.com.springrestful.utils.MathCalc.isNumeric;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PersonServices personServices;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable(value= "id") String id) throws Exception {
        return personServices.findById(id);
    }
}
