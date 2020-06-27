package br.com.ibyte.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ibyte.api.util.ResourceUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroSetorIntegrationTests {
	
	private static final int SETOR_TI = 1;
	
	@LocalServerPort
	private int port;
	
	private String jsonCadastroSetor;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/api/setores";
		
		jsonCadastroSetor = ResourceUtil.getContentFromResource(
				"/json/cadastro-setor.json");
	}
	
	@Test
	public void deveRetornarStatus200_QuandoListarSetores() {
		RestAssured.given()
			.auth().basic("ibyte", "ibyte")
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarSetorInexistente() {
		RestAssured.given()
			.auth().basic("ibyte", "ibyte")
			.pathParam("idSetor", 100)
			.accept(ContentType.JSON)
		.when()
			.get("/{idSetor}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarSetor() {
		RestAssured.given()
		.auth().basic("ibyte", "ibyte")
			.body(jsonCadastroSetor)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarPessoasDeUmSetorEspecifico() {
		RestAssured.given()
		.auth().basic("ibyte", "ibyte")
		.pathParam("idSetor", SETOR_TI)
		.accept(ContentType.JSON)
	.when()
		.get("/{idSetor}/pessoas")
	.then()
		.statusCode(200);
	}
	
	@Test
	public void deveRetornarStatus409_QuandoTentarExcluirUmSetorComPessoasAtrelada() {
		RestAssured.given()
		.auth().basic("ibyte", "ibyte")
		.pathParam("idSetor", SETOR_TI)
		.accept(ContentType.JSON)
	.when()
		.delete("/{idSetor}")
	.then()
		.statusCode(409);
	}
	
}
