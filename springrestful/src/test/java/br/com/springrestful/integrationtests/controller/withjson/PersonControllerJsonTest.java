package br.com.springrestful.integrationtests.controller.withjson;

import br.com.springrestful.configs.TestConfigs;
import br.com.springrestful.integrationtests.vo.PersonVO;
import br.com.springrestful.it.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static PersonVO personVO;

	@BeforeAll
	public static void setup(){
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		personVO = new PersonVO();
	}

	@Test
	@Order(1)
	public void testeCreate() throws IOException {
		mockPerson();
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content =
			given().spec(specification)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(personVO)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);

		Assert.assertNotNull(createdPerson);
		Assert.assertNotNull(createdPerson.getId());
		Assert.assertNotNull(createdPerson.getAddress());
		Assert.assertNotNull(createdPerson.getFirstName());
		Assert.assertNotNull(createdPerson.getGender());
		Assert.assertNotNull(createdPerson.getLastName());

		Assert.assertTrue(createdPerson.getId() > 0);

		Assert.assertEquals("Evillyn", createdPerson.getFirstName());
		Assert.assertEquals("Oliveira", createdPerson.getLastName());
		Assert.assertEquals("Muié", createdPerson.getGender());
		Assert.assertEquals("Naquela rua ali descendo", createdPerson.getAddress());
	}

	@Test
	@Order(2)
	public void testeCreateWithWrongOrigin() throws IOException {
		mockPerson();
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content =
				given().spec(specification)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.body(personVO)
						.when()
						.post()
						.then()
						.statusCode(403)
						.extract()
						.body()
						.asString();

		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);

		Assert.assertNotNull(content);
		Assert.assertEquals("Invalid CORS request", content);
	}

	@Test
	@Order(3)
	public void testeFindById() throws IOException {
		mockPerson();
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content =
				given().spec(specification)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.pathParams("id", personVO.getId())
						.when()
							.get("{id}")
						.then()
							.statusCode(200)
								.extract()
								.body()
								.asString();

		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);

		Assert.assertNotNull(createdPerson);
		Assert.assertNotNull(createdPerson.getId());
		Assert.assertNotNull(createdPerson.getAddress());
		Assert.assertNotNull(createdPerson.getFirstName());
		Assert.assertNotNull(createdPerson.getGender());
		Assert.assertNotNull(createdPerson.getLastName());

		Assert.assertTrue(createdPerson.getId() > 0);

		Assert.assertEquals("Evillyn", createdPerson.getFirstName());
		Assert.assertEquals("Oliveira", createdPerson.getLastName());
		Assert.assertEquals("Muié", createdPerson.getGender());
		Assert.assertEquals("Naquela rua ali descendo", createdPerson.getAddress());
	}

	@Test
	@Order(4)
	public void testeFindByIdWithWrongCors() throws IOException {
		mockPerson();
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content =
				given().spec(specification)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.pathParams("id", personVO.getId())
						.when()
						.get("{id}")
						.then()
						.statusCode(200)
						.extract()
						.body()
						.asString();

		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);

		Assert.assertNotNull(content);
		Assert.assertEquals("Invalid CORS request", content);
	}

	private void mockPerson() {
		personVO.setId(1L);
		personVO.setFirstName("Evillyn");
		personVO.setLastName("Oliveira");
		personVO.setGender("Muié");
		personVO.setAddress("Naquela rua ali descendo");
	}

}