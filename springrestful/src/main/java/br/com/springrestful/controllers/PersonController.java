package br.com.springrestful.controllers;

import br.com.springrestful.data.vo.v2.PersonVOV2;
import br.com.springrestful.services.PersonServices;
import br.com.springrestful.data.vo.v1.PersonVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.springrestful.util.MediaType;

import java.util.List;

@RestController
@RequestMapping("api/person")
@Tag(name = "People", description = "Endpoints de Pessoa")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(value = "/v1",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Retorna lista de pessoas", description = "Retorna lista de pessoas", tags = {"People"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page, limit, Sort.by(sortDirection, "firstName"));

        return ResponseEntity.ok(personServices.findAll(pageable));
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://google.com"})
    @GetMapping(value = "/v1/{id}",
            produces =  { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Retorna pessoa pelo Id", description = "Retorna pessoa pelo Id", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public PersonVO findById(@PathVariable(value= "id") Long id){
        return personServices.findById(id);
    }

    @GetMapping(value = "/v1/name/{firstName}",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Retorna pessoas pelo nome", description = "Retorna pessoas pelo nome", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonByName(
            @PathVariable(value = "firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page, limit, Sort.by(sortDirection, "firstName"));

        return ResponseEntity.ok(personServices.findPersonByName(firstName, pageable));
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://google.com"})
    @PostMapping(value = "/v1",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
            produces =  { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Cadastra pessoa", description = "Cadastra pessoa", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public PersonVO create(@RequestBody PersonVO person){
        return personServices.create(person);
    }

    @PostMapping(value = "/v2",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
            produces =  { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Cadastra pessoa", description = "Cadastra pessoa", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person){
        return personServices.createV2(person);
    }


    @PutMapping(
            value = "v1/{id}",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
            produces =  { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Edita pessoa cadastrada", description = "Edita pessoa cadastrada", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public PersonVO update(@RequestBody PersonVO person){
        return personServices.update(person);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://google.com"})
    @PatchMapping(value = "/v1/{id}",
            produces =  { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Retorna pessoa desativada", description = "Retorna pessoa desativada", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = PersonVO.class))
                    }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public PersonVO disablePerson(@PathVariable(value= "id") Long id){
        return personServices.disablePerson(id);
    }

    @DeleteMapping(
            value = "v1/{id}")
    @Operation(summary = "Exclui pessoa cadastrada", description = "Exclui pessoa cadastrada", tags = {"People"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> delete(@PathVariable(value= "id") Long id){

        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
