package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Instalacao;
import com.epcafes.model.Propriedade;

public interface DepreciacaoInstalacaoRepository extends JpaRepository<DepreciacaoInstalacao, Long> {

	@Query("SELECT d FROM DepreciacaoInstalacao d "
			+ "WHERE d.propriedade = :propriedade")
	List<DepreciacaoInstalacao> findAllByPropriedade(Propriedade propriedade);
	
	List<DepreciacaoInstalacao> findAllByInstalacao(Instalacao instalacao);
}
