package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.Instalacao;
import com.epcafes.model.Propriedade;

public interface InstalacaoRepository extends JpaRepository<Instalacao, Long>{
		
	Instalacao findById(long id);
	
	@Query("select i from Instalacao i where i.propriedade = :propriedade")
	List<Instalacao> findAllByPropriedade(Propriedade propriedade);
}
