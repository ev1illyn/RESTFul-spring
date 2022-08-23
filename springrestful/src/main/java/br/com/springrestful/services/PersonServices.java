package br.com.springrestful.services;

import br.com.springrestful.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    //ao invés de usar log4g
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String id) throws RuntimeException {
        logger.info("Api está buscando uma pessoa!");

        //mock enquanto não tem banco configurado
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setName("Evy");
        person.setLastName("Loki");
        person.setAddress("JuaCity");
        person.setGender("Muié");
        return person;
    }

}
