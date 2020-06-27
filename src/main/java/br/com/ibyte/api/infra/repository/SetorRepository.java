package br.com.ibyte.api.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ibyte.api.domain.model.Setor;

public interface SetorRepository extends JpaRepository<Setor, Long> {
	
	Setor findByNome(String nome);

}
