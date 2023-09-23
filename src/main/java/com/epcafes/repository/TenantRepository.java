package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    
}
