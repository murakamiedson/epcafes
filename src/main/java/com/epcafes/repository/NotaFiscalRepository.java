package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.NotaFiscal;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long>{

    NotaFiscal findById(long id);
    
}
