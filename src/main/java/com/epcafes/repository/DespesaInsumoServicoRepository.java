package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.epcafes.model.DespesaOutrosInsumoServico;

public interface DespesaInsumoServicoRepository extends JpaRepository<DespesaOutrosInsumoServico, Long>{
	public List<DespesaOutrosInsumoServico> findByTenantId(Long tenantId);
}
