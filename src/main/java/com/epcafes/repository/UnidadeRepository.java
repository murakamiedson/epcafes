package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.Unidade;

public interface UnidadeRepository extends JpaRepository<Unidade, Long>{
	public List<Unidade> findByTenantId(Long tenantId);
}
