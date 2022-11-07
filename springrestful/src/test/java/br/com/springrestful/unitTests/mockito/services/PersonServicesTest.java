package br.com.springrestful.unitTests.mockito.services;

import br.com.springrestful.MockPerson;
import br.com.springrestful.data.vo.v1.PersonVO;
import br.com.springrestful.exceptions.RequiredObjectIsNullException;
import br.com.springrestful.models.Person;
import br.com.springrestful.repositories.PersonRepository;
import br.com.springrestful.services.PersonServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices personServices;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();

        when(personRepository.findAll()).thenReturn(list);

        var people = personServices.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var peopleOne = people.get(1);

        assertNotNull(peopleOne);
        assertNotNull(peopleOne.getKey());
        assertNotNull(peopleOne.getLinks());

        assertNotNull(peopleOne.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", peopleOne.getAddress());
        assertEquals("First Name Test1", peopleOne.getFirstName());
        assertEquals("Last Name Test1", peopleOne.getLastName());
        assertEquals("Female", peopleOne.getGender());

    }

    @Test
    void findById() {
        Person entity = input.mockEntity();
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        var result = personServices.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test0", result.getAddress());
        assertEquals("First Name Test0", result.getFirstName());
        assertEquals("Last Name Test0", result.getLastName());
        assertEquals("Male", result.getGender());
    }

    @Test
    void update() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(personRepository.save(entity)).thenReturn(persisted);

        var result = personServices.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result);

        assertNotNull(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void create() {
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(personRepository.save(entity)).thenReturn(persisted);

        var result = personServices.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result);

        assertNotNull(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void createWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    personServices.create(null);
        });

        String expectedMessage = "Não é permitido persistir um objeto nulo!";
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    personServices.update(null);
                });

        String expectedMessage = "Não é permitido persistir um objeto nulo!";
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        personServices.delete(1L);

    }
}