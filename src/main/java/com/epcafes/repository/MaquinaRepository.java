package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.epcafes.model.Maquina;

@Repository
public interface MaquinaRepository extends JpaRepository<Maquina, Long> {
    List<Maquina> findByTenantId(Long tenant_id);

}
