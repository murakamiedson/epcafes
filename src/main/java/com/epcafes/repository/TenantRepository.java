package com.epcafes.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epcafes.model.Tenant;



@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long> {
}
