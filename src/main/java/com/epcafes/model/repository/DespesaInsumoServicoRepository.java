package com.epcafes.model.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.epcafes.model.DespesaInsumoServico;

public interface DespesaInsumoServicoRepository extends CrudRepository<DespesaInsumoServico, Long>{
	public List<DespesaInsumoServico> findByTenantId(Long tenantId);
}
