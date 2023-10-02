package com.arquitetura.epcafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arquitetura.epcafe.model.DespesaMaquina;


@Repository
public interface DespesaMaquinaRepository extends JpaRepository<DespesaMaquina, Long>{
    
    DespesaMaquina findById(long id);
}
