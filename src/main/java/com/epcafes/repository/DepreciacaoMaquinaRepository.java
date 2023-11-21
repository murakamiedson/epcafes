package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.dto.DepreciacaoMaquinaDTO;
import com.epcafes.model.DepreciacaoMaquina;
import com.epcafes.model.Maquina;

public interface DepreciacaoMaquinaRepository extends JpaRepository<DepreciacaoMaquina, Long> {

	@Query("SELECT d FROM DepreciacaoMaquina d "
			+ "WHERE d.tenant_id = :tenantId")
	List<DepreciacaoMaquina> findAllByTenantId(Long tenantId);
	
	List<DepreciacaoMaquina> findAllByMaquina(Maquina maquina);
	
	//retorna uma lista de DepreciacaoMaquinaDTO de uma Maquina
	@Query("SELECT new com.epcafes.dto.DepreciacaoMaquinaDTO(d.id, d.horasTrabalhadas, "
			+ "d.porcentagemUtilizacao, d.valorDepreciacao, "
			+ "m.id, m.nome, m.valor, m.valorResidual, m.vidaUtilHoras) FROM DepreciacaoMaquina d "
			+ "INNER JOIN Maquina m ON d.maquina.id = m.id "
			+ "WHERE m = :maquina "
			+ "AND m.tenantId = :tenantId "
			+ "ORDER BY d.maquina.id")
    List<DepreciacaoMaquinaDTO> buscarDepreciacoesMaquinaDTO(Maquina maquina, Long tenantId);
}
