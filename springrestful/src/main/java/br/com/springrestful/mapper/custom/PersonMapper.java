package br.com.springrestful.mapper.custom;

import br.com.springrestful.data.vo.v2.PersonVOV2;
import br.com.springrestful.models.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVo(Person person){
        PersonVOV2 voV2 = new PersonVOV2();
        voV2.setKey(person.getId());
        voV2.setAddress(person.getAddress());
        voV2.setFirstName(person.getFirstName());
        voV2.setLastName(person.getLastName());
        voV2.setGender(person.getGender());
        voV2.setBirthDate(new Date());
        return voV2;
    }

    public Person convertVoToEntity(PersonVOV2 personVOV2){
        Person entity = new Person();
        entity.setId(personVOV2.getKey());
        entity.setAddress(personVOV2.getAddress());
        entity.setFirstName(personVOV2.getFirstName());
        entity.setLastName(personVOV2.getLastName());
        entity.setGender(personVOV2.getGender());
        return entity;
    }
}
