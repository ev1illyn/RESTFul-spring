package br.com.springrestful.services;

import br.com.springrestful.controllers.PersonController;
import br.com.springrestful.data.vo.v1.PersonVO;
import br.com.springrestful.data.vo.v2.PersonVOV2;
import br.com.springrestful.exceptions.RequiredObjectIsNullException;
import br.com.springrestful.exceptions.ResourceNotFoundException;
import br.com.springrestful.mapper.DozerMapper;
import br.com.springrestful.mapper.custom.PersonMapper;
import br.com.springrestful.models.Person;
import br.com.springrestful.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices  {

    //ao invés de usar log4j
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;

    @Autowired
    PersonMapper personMapper;

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable){
        logger.info("Api está buscando todas as pessoa!");

        var personPage = repository.findAll(pageable);

        var personVOsPage = personPage.map(p ->
                DozerMapper.parseObject(p, PersonVO.class));

        personVOsPage.map(
                p -> p.add(linkTo(methodOn(PersonController.class)
                        .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageNumber(),
                        "asc")).withSelfRel();
        return assembler.toModel(personVOsPage, link);

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

    public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstName, Pageable pageable){
        logger.info("Api está buscando uma pessoa por nome!");

        var personPage = repository.findPersonByName(firstName, pageable);

        var personVOsPage = personPage.map(p ->
                DozerMapper.parseObject(p, PersonVO.class));

        personVOsPage.map(
                p -> p.add(linkTo(methodOn(PersonController.class)
                        .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageNumber(),
                        "asc")).withSelfRel();
        return assembler.toModel(personVOsPage, link);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Criando uma pessoa!");

        if (person == null) throw new RequiredObjectIsNullException();
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

        if (person == null) throw new RequiredObjectIsNullException();
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

    @Transactional
    public PersonVO disablePerson(Long id){
        logger.info("Desativa uma pessoa!");

        repository.disablePerson(id);
        var entity = repository.findById(id)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deletando uma pessoa!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        repository.delete(entity);

    }

}
