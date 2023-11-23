package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.dto.DepreciacaoInstalacaoDTO;
import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Instalacao;
import com.epcafes.model.Propriedade;

public interface DepreciacaoInstalacaoRepository extends JpaRepository<DepreciacaoInstalacao, Long> {

	@Query("SELECT d FROM DepreciacaoInstalacao d "
			+ "WHERE d.propriedade = :propriedade")
	List<DepreciacaoInstalacao> findAllByPropriedade(Propriedade propriedade);
	
	List<DepreciacaoInstalacao> findAllByInstalacao(Instalacao instalacao);
	
	//retorna uma lista de DepreciacaoInstalacaoDTO de uma Instalação
	@Query("SELECT new com.epcafes.dto.DepreciacaoInstalacaoDTO(d.id, d.porcentagemUtilizacao, "
			+ "d.valorDepreciacao, i.id, i.nome, i.valor, "
			+ "i.valorResidual, i.vidaUtilAnos) FROM DepreciacaoInstalacao d "
			+ "INNER JOIN Instalacao i ON d.instalacao.id = i.id "
			+ "WHERE i = :instalacao "
			+ "AND i.propriedade = :propriedade "
			+ "ORDER BY d.instalacao.id")
    List<DepreciacaoInstalacaoDTO> buscarDepreciacoesInstalacaoDTO(Instalacao instalacao, Propriedade propriedade);
}
