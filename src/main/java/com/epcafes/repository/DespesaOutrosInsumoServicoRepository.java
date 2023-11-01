package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.epcafes.model.DespesaOutrosInsumoServico;
import com.epcafes.model.Tenant;

public interface DespesaOutrosInsumoServicoRepository extends JpaRepository<DespesaOutrosInsumoServico, Long>{
	public List<DespesaOutrosInsumoServico> findByTenant(Tenant tenant);
}
