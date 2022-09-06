package br.com.springrestful.services;

import br.com.springrestful.data.vo.v1.PersonVO;
import br.com.springrestful.data.vo.v2.PersonVOV2;
import br.com.springrestful.exceptions.ResourceNotFoundException;
import br.com.springrestful.mapper.DozerMapper;
import br.com.springrestful.mapper.custom.PersonMapper;
import br.com.springrestful.models.Person;
import br.com.springrestful.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        return DozerMapper
                .parseListObjects(repository.findAll(),
                        PersonVO.class);

    }

    public PersonVO findById(Long id){
        logger.info("Api está buscando uma pessoa!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Criando uma pessoa!");

        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 personVOV2) {
        logger.info("Criando uma pessoa V2!");

        var entity = personMapper.convertVoToEntity(personVOV2);
        var vo = personMapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Atualizando uma pessoa!");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deletando uma pessoa!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum resultado."));

        repository.delete(entity);

    }

}
