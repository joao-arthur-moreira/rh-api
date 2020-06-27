package br.com.ibyte.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibyte.api.domain.model.Pessoa;
import br.com.ibyte.api.domain.service.PessoaService;
import br.com.ibyte.api.infra.repository.PessoaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Pessoas")
@RestController
@RequestMapping(path = "/api/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@ApiOperation("Listar todas as pessoas")
	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	@ApiOperation("Buscar pessoa por id")
	@GetMapping("/{idPessoa}")
	public Pessoa porId(@PathVariable Long idPessoa) {	
		return pessoaService.buscar(idPessoa);
	}
	
	@ApiOperation("Cadastrar pessoa")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Pessoa adicionar(@Valid @RequestBody Pessoa pessoa) {
		return pessoaService.salvar(pessoa);
	}
	
	@ApiOperation("Alterar cadastro de pessoa")
	@PutMapping("/{idPessoa}")
	public Pessoa atualizar(@PathVariable Long idPessoa, @RequestBody @Valid Pessoa pessoa) {
		return pessoaService.alterar(idPessoa, pessoa);
	}
	
	@ApiOperation("Excluir pessoa")
	@DeleteMapping("/{idPessoa}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idPessoa) {
		pessoaService.excluir(idPessoa);
	}
	
}
