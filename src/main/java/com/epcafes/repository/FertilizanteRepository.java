package com.epcafes.repository;

import java.util.List;
//import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.epcafes.model.Fertilizante;

public interface FertilizanteRepository extends CrudRepository<Fertilizante, Long> {

    List<Fertilizante> findByTenantId(Long tenant_id);

    // Excluir depois caso nao de pau
    // Optional<Fertilizante> findFertilizanteById(long id);

}
