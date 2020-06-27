package br.com.ibyte.api.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibyte.api.domain.exception.NegocioException;
import br.com.ibyte.api.domain.exception.PessoaNaoEncontradaException;
import br.com.ibyte.api.domain.model.Pessoa;
import br.com.ibyte.api.domain.model.Setor;
import br.com.ibyte.api.infra.repository.PessoaRepository;
import br.com.ibyte.api.infra.repository.SetorRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private SetorRepository setorRepository;

	public Pessoa buscar(Long idPessoa) {
		return pessoaRepository.findById(idPessoa).orElseThrow(() -> new PessoaNaoEncontradaException(idPessoa));
	}
	
	public Pessoa alterar(Long idPessoa, Pessoa pessoa) {
		Pessoa pessoaExistente = buscar(idPessoa);
		
		validarSetor(pessoa);
		
		pessoa.setId(pessoaExistente.getId());
		
		return pessoaRepository.save(pessoa);
	}

	public Pessoa salvar(Pessoa pessoa) {
		pessoa.setId(null);
		
		validarSetor(pessoa);		
		
		return pessoaRepository.save(pessoa);
	}

	public void excluir(Long idPessoa) {
		Pessoa pessoaExistente = buscar(idPessoa);
		
		pessoaRepository.deleteById(pessoaExistente.getId());		
	}
	
	private void validarSetor(Pessoa pessoa) {		
		if (pessoa.getSetor() != null) {
			Optional<Setor> setorOptional = setorRepository.findById(pessoa.getSetor().getId());
			if (setorOptional.isPresent()) {				
				pessoa.setSetor(setorOptional.get());
			} else {
				throw new NegocioException("Não existe um cadastro de setor com o código " + pessoa.getSetor().getId());
			}
		}
	}
}
