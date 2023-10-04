package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.Maquina;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

        Maquina findById(long id);
}
