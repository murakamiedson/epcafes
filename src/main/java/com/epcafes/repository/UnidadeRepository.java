package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.Unidade;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    
}
