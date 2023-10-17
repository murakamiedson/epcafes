package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.Propriedade;

public interface PropriedadeRepository extends JpaRepository<Propriedade, Long>{
	public List<Propriedade> findByTenantId(Long tenantId);
}
