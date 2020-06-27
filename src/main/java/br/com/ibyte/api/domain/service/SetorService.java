package br.com.ibyte.api.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibyte.api.domain.exception.EntidadeEmUsoException;
import br.com.ibyte.api.domain.exception.SetorNaoEncontradoException;
import br.com.ibyte.api.domain.model.Pessoa;
import br.com.ibyte.api.domain.model.Setor;
import br.com.ibyte.api.infra.repository.PessoaRepository;
import br.com.ibyte.api.infra.repository.SetorRepository;

@Service
public class SetorService {
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Setor salvar(Setor setor) {
		setor.setId(null);
		return setorRepository.save(setor);
	}

	public Setor alterar(Long idSetor, Setor setor) {
		Setor setorExistente = buscar(idSetor);
		
		setor.setId(setorExistente.getId());
		
		return setorRepository.save(setor);
	}
	
	public Setor buscar(Long idSetor) {
		return setorRepository.findById(idSetor).orElseThrow(() -> new SetorNaoEncontradoException(idSetor));
	}

	public void excluir(Long idSetor) {		
		Setor setorExistente = buscar(idSetor);
		
		List<Pessoa> pessoasDoSetor = pessoaRepository.findBySetor(setorExistente);
		
		if (pessoasDoSetor.size() > 0) {
			throw new EntidadeEmUsoException("Entidade em uso, não é possível excluir");
		}
		
		setorRepository.deleteById(idSetor);
	}

}
