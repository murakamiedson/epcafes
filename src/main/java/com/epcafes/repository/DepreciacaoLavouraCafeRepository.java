package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.DepreciacaoLavouraCafe;
import com.epcafes.model.Propriedade;

public interface DepreciacaoLavouraCafeRepository extends JpaRepository<DepreciacaoLavouraCafe, Long>  {

	@Query("SELECT d FROM DepreciacaoLavouraCafe d "
			+ "WHERE d.propriedade = :propriedade")
	List<DepreciacaoLavouraCafe> findAllByPropriedade(Propriedade propriedade);
	
}
