package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epcafes.model.DespesaFertilizante;

@Repository
public interface DespesaFertilizanteRepository extends JpaRepository<DespesaFertilizante, Long>{
    
    DespesaFertilizante findById(long id);
}
