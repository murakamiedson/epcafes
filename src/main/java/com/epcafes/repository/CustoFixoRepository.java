package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.CustoFixo;

public interface CustoFixoRepository extends JpaRepository<CustoFixo, Long> {
	
	@Query("SELECT c FROM CustoFixo c "
			+ "WHERE c.tenant_id = :tenantId")
	List<CustoFixo> findAllByTenantId(Long tenantId);
}
