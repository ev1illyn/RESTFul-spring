package br.com.springrestful.services;

import br.com.springrestful.exceptions.ResourceNotFoundException;
import br.com.springrestful.models.Person;
import br.com.springrestful.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    //ao invés de usar log4g
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll(){
        logger.info("Api está buscando todas as pessoa!");

        List<Person> persons = new ArrayList<>();
        return repository.findAll();

    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setFirstName("Nome da pessoa: " + i);
        person.setLastName("Sobrenome: " + i);
        person.setAddress("JuaCity");
        person.setGender("Muié");
        return person;
    }

    public Person create(Person person) {
        logger.info("Criando uma pessoa!");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Atualizando uma pessoa!");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deletando uma pessoa!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        repository.delete(entity);

    }

    public Person findById(Long id){
        logger.info("Api está buscando uma pessoa!");

        //mock enquanto não tem banco configurado
        Person person = new Person();
        person.setFirstName("Evy");
        person.setLastName("Loki");
        person.setAddress("JuaCity");
        person.setGender("Muié");
        return repository.findById(id)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Não foi encontrado nenhum resultado."));
    }

}
