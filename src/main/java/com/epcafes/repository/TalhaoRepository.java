package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;

public interface TalhaoRepository extends JpaRepository<Talhao, Long>{
	
	
	Talhao findById(long id);
	
	@Query("select t from Talhao t where t.propriedade = :propriedade")
	List<Talhao> findAllByPropriedade(Propriedade propriedade);

}