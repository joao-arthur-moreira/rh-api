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
import br.com.ibyte.api.domain.model.Setor;
import br.com.ibyte.api.domain.service.SetorService;
import br.com.ibyte.api.infra.repository.PessoaRepository;
import br.com.ibyte.api.infra.repository.SetorRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Setores")
@RestController
@RequestMapping(path = "/api/setores")
public class SetorController {
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private SetorService setorService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@ApiOperation("Listar todas os setores")
	@GetMapping
	public List<Setor> listar() {
		return setorRepository.findAll();
	}
	
	@ApiOperation("Buscar setor por código")
	@GetMapping("/{idSetor}")
	public Setor porId(@PathVariable Long idSetor) {	
		return setorService.buscar(idSetor);
	}
	
	@ApiOperation("Cadastrar setor")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Setor adicionar(@Valid @RequestBody Setor setor) {
		return setorService.salvar(setor);
	}
	
	@ApiOperation("Alterar cadastro do setor")
	@PutMapping("/{idSetor}")
	public Setor atualizar(@PathVariable Long idSetor, @Valid @RequestBody Setor setor) {
		return setorService.alterar(idSetor, setor);
	}
	
	@ApiOperation("Excluir setor")
	@DeleteMapping("/{idSetor}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idSetor) {
		setorService.excluir(idSetor);
	}
	
	@ApiOperation("Listar pessoas de um setor específico")
	@GetMapping("/{idSetor}/pessoas")
	public List<Pessoa> pessoaDoSetor(@PathVariable Long idSetor) {	
		return pessoaRepository.findBySetorId(idSetor);
	}

}
