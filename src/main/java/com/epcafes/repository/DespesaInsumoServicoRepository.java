package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.epcafes.model.DespesaInsumoServico;

public interface DespesaInsumoServicoRepository extends JpaRepository<DespesaInsumoServico, Long>{
	public List<DespesaInsumoServico> findByTenantId(Long tenantId);
}
