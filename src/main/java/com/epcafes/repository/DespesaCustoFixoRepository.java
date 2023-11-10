package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.dto.DespesaCustoFixoDTO;
import com.epcafes.model.CustoFixo;
import com.epcafes.model.DespesaCustoFixo;

public interface DespesaCustoFixoRepository extends JpaRepository<DespesaCustoFixo, Long> {
	
	@Query("SELECT d FROM DespesaCustoFixo d "
			+ "WHERE d.tenant_id = :tenantId")
	List<DespesaCustoFixo> findAllByTenantId(Long tenantId);
	
	//retorna todas as despesas de um determinado custoFixo
	List<DespesaCustoFixo> findAllByCustoFixo(CustoFixo custoFixo);
	
	//retorna todos os anos que tem alguma despesa cadastrada
	@Query("SELECT FUNCTION('YEAR', d.mesAno) FROM DespesaCustoFixo d GROUP BY FUNCTION('YEAR', d.mesAno)")
    List<Integer> buscarAnos();
	
	//retorna uma lista de DespesaCustoFixoDTO de um CustoFixo em um determinado ano
	@Query("SELECT new com.epcafes.dto.DespesaCustoFixoDTO(d.id, d.valor, d.mesAno, "
			+ "d.porcentagemUtilizacao, c.id, c.nome) FROM DespesaCustoFixo d "
			+ "INNER JOIN CustoFixo c ON d.custoFixo.id = c.id "
			+ "WHERE FUNCTION('YEAR', d.mesAno) = :ano "
			+ "AND c = :custoFixo "
			+ "AND d.tenant_id = :tenantId "
			+ "ORDER BY d.custoFixo.id")
    List<DespesaCustoFixoDTO> buscarDespesasCustoFixoDTO(CustoFixo custoFixo, int ano, Long tenantId);
}
