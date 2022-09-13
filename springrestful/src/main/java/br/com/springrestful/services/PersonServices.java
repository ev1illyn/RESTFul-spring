package br.com.springrestful.services;

import br.com.springrestful.controllers.PersonController;
import br.com.springrestful.data.vo.v1.PersonVO;
import br.com.springrestful.data.vo.v2.PersonVOV2;
import br.com.springrestful.exceptions.ResourceNotFoundException;
import br.com.springrestful.mapper.DozerMapper;
import br.com.springrestful.mapper.custom.PersonMapper;
import br.com.springrestful.models.Person;
import br.com.springrestful.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    //ao invés de usar log4g
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper personMapper;

    public List<PersonVO> findAll(){
        logger.info("Api está buscando todas as pessoa!");

        var persons = DozerMapper
                .parseListObjects(repository.findAll(),
                        PersonVO.class);
        persons
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;

    }

    public PersonVO findById(Long id){
        logger.info("Api está buscando uma pessoa!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {
        logger.info("Criando uma pessoa!");

        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 personVOV2) {
        logger.info("Criando uma pessoa V2!");

        var entity = personMapper.convertVoToEntity(personVOV2);
        var vo = personMapper.convertEntityToVo(repository.save(entity));
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Atualizando uma pessoa!");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deletando uma pessoa!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        repository.delete(entity);

    }

}
