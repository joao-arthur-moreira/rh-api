package br.com.ibyte.api.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.ibyte.api.domain.model.EmployerMockApi;
import br.com.ibyte.api.domain.model.Pessoa;
import br.com.ibyte.api.domain.model.Setor;
import br.com.ibyte.api.infra.repository.PessoaRepository;
import br.com.ibyte.api.infra.repository.SetorRepository;
import reactor.core.publisher.Mono;

@Service
public class EmployerMockApiService {
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	public List<EmployerMockApi> listar() {
		List<EmployerMockApi> lista = new ArrayList<>();
		
		Mono<ResponseEntity<List<EmployerMockApi>>> monoEmployerMockApi = webClient
				.method(HttpMethod.GET)
				.uri("/employer")
				.retrieve()			
				.toEntityList(EmployerMockApi.class);
		
		lista = monoEmployerMockApi.block().getBody();
		
		return lista;
	}

	public void importar() {
		List<EmployerMockApi> employees = listar();
		employees.forEach(e -> {
			String nomeCompleto = e.getFirst_name() + " " + e.getLast_name();
			Pessoa pessoaExistente = pessoaRepository.findByNome(nomeCompleto);
			
			String nomeSetor = e.getDepartament();
			Setor setorExistente = setorRepository.findByNome(nomeSetor);			
			
			if (setorExistente == null) {
				setorExistente = cadastraSetor(nomeSetor);
			}
			
			if (pessoaExistente == null) {
				cadastraPessoa(setorExistente, nomeCompleto, e.getCareer());
			}
		});
	}

	private void cadastraPessoa(Setor setorExistente, String nomeCompleto, String descricaoCargo) {		
		Pessoa novaPessoa = new Pessoa();
		novaPessoa.setNome(nomeCompleto);
		novaPessoa.setSetor(setorExistente);
		novaPessoa.setDescricaoCargo(descricaoCargo);
		
		pessoaRepository.save(novaPessoa);
	}

	private Setor cadastraSetor(String nomeSetor) {
		Setor novoSetor = new Setor();
		novoSetor.setNome(nomeSetor);
		return setorRepository.save(novoSetor);
	}

}
