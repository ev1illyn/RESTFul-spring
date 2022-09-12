package br.com.springrestful.controllers;

import br.com.springrestful.data.vo.v2.PersonVOV2;
import br.com.springrestful.services.PersonServices;
import br.com.springrestful.data.vo.v1.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(value = "/v1",
            produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public List<PersonVO> findAll(){
        return personServices.findAll();
    }

    @GetMapping(value = "/v1/{id}",
            produces =  { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public PersonVO findById(@PathVariable(value= "id") Long id){
        return personServices.findById(id);
    }

    @PostMapping(value = "/v1",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces =  { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public PersonVO create(@RequestBody PersonVO person){
        return personServices.create(person);
    }

    @PostMapping(value = "/v2",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces =  { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person){
        return personServices.createV2(person);
    }


    @PutMapping(
            value = "v1/{id}",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces =  { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public PersonVO update(@RequestBody PersonVO person){
        return personServices.update(person);
    }

    @DeleteMapping(
            value = "v1/{id}")
    public ResponseEntity<?> delete(@PathVariable(value= "id") Long id){

        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
