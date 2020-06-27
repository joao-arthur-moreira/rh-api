package br.com.ibyte.api.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ibyte.api.domain.model.Pessoa;
import br.com.ibyte.api.domain.model.Setor;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	List<Pessoa> findBySetor(Setor setor);
	
	List<Pessoa> findBySetorId(Long idSetor);
	
	Pessoa findByNome(String nome);

}
