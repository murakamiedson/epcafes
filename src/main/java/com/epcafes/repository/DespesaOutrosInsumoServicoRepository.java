package com.epcafes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epcafes.model.DespesaOutrosInsumoServico;
import com.epcafes.model.Propriedade;

public interface DespesaOutrosInsumoServicoRepository extends JpaRepository<DespesaOutrosInsumoServico, Long>{
	public List<DespesaOutrosInsumoServico> findByPropriedade(Propriedade p);
	
	@Query("SELECT d FROM DespesaOutrosInsumoServico d")
    Page<DespesaOutrosInsumoServico> findPaginated(Pageable pageable);
}
