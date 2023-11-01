package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epcafes.model.Fertilizante;

@Repository
public interface FertilizanteRepository extends JpaRepository<Fertilizante, Long> {

    List<Fertilizante> findByTenantId(Long tenant_id);

    // Excluir depois caso nao de pau
    // Optional<Fertilizante> findFertilizanteById(long id);

}
