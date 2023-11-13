package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.CustoFixo;
import com.epcafes.model.Propriedade;

public interface CustoFixoRepository extends JpaRepository<CustoFixo, Long> {
	
	@Query("SELECT c FROM CustoFixo c "
			+ "WHERE c.propriedade = :propriedade")
	List<CustoFixo> findAllByPropriedade(Propriedade propriedade);
}
