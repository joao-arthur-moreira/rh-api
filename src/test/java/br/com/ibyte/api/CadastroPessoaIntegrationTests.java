package br.com.ibyte.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ibyte.api.util.ResourceUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroPessoaIntegrationTests {
	
	@LocalServerPort
	private int port;
	
	private String jsonCadastroPessoaComNomeVazio;
	
	private String jsonCadastroPessoaComSetorInexistente;
	
	private String jsonCadastroPessoaAlterar;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/api/pessoas";
		
		jsonCadastroPessoaComNomeVazio = ResourceUtil.getContentFromResource(
				"/json/cadastro-pessoa-com-nome-vazio.json");
		
		jsonCadastroPessoaComSetorInexistente = ResourceUtil.getContentFromResource(
				"/json/cadastro-pessoa-com-setor-inexistente.json");
		
		jsonCadastroPessoaAlterar = ResourceUtil.getContentFromResource(
				"/json/cadastro-pessoa-alterar.json");		
	}

	@Test
	public void deveRetornarStatus200_QuandoListarPessoas() {
		RestAssured.given()
			.auth().basic("ibyte", "ibyte")
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveRetornarStatus400_QuandoTentarCadastrarUmaPessoaComNomeVazio() {
		RestAssured.given()
		.auth().basic("ibyte", "ibyte")
			.body(jsonCadastroPessoaComNomeVazio)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(400);
	}
	
	@Test
	public void deveRetornarStatus400_QuandoTentarCadastrarUmaPessoaComSetorInexistente() {
		RestAssured.given()
		.auth().basic("ibyte", "ibyte")
			.body(jsonCadastroPessoaComSetorInexistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(400);
	}
	
	@Test
	public void deveRetornarStatus204_QuandoExcluirUmaPessoaExistente() {
		RestAssured.given()
		.auth().basic("ibyte", "ibyte")
		.pathParam("idPessoa", 1)
		.when()
			.delete("/{idPessoa}")
		.then()
			.statusCode(204);
	}
	
	@Test
	public void deveRetornarStatus404_QuandoTentarExcluirUmaPessoaInexistente() {
		RestAssured.given()
		.auth().basic("ibyte", "ibyte")
		.pathParam("idPessoa", 100)
		.when()
			.delete("/{idPessoa}")
		.then()
			.statusCode(404);
	}
	
	@Test
	public void deveRetornarStatus404_QuandoTentarAlterarUmaPessoaInexistente() {
		RestAssured.given()
			.auth().basic("ibyte", "ibyte")
			.pathParam("idPessoa", 100)
			.body(jsonCadastroPessoaAlterar)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{idPessoa}")
		.then()
			.statusCode(404);
	}

}
