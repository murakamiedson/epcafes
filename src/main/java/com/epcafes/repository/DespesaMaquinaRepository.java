package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.DespesaMaquina;

public interface DespesaMaquinaRepository extends JpaRepository<DespesaMaquina, Long>{
    
    DespesaMaquina findById(long id);
}
