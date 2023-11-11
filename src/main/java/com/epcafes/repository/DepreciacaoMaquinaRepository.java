package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.DepreciacaoMaquina;

public interface DepreciacaoMaquinaRepository extends JpaRepository<DepreciacaoMaquina, Long> {

	@Query("SELECT d FROM DepreciacaoMaquina d "
			+ "WHERE d.tenant_id = :tenantId")
	List<DepreciacaoMaquina> findAllByTenantId(Long tenantId);
}
