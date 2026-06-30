package com.app.notaFlow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.notaFlow.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	
	List<Avaliacao> findByAlunoId(Long alunoId);

}
