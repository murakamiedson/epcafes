package com.epcafes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.Cultivo;

public interface CultivoRepository extends JpaRepository<Cultivo, Long>  {

}
