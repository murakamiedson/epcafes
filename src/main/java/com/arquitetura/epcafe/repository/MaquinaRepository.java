package com.arquitetura.epcafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arquitetura.epcafe.model.Maquina;

@Repository
public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

        Maquina findById(long id);
}
