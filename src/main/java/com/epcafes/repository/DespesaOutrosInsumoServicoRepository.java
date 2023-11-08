package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.DespesaOutrosInsumoServico;

public interface DespesaOutrosInsumoServicoRepository extends JpaRepository<DespesaOutrosInsumoServico, Long>{
	public List<DespesaOutrosInsumoServico> findByTenantId(Long tenantId);
}
