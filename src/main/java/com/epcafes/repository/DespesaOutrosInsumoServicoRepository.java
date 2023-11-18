package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.DespesaOutrosInsumoServico;
import com.epcafes.model.Propriedade;

public interface DespesaOutrosInsumoServicoRepository extends JpaRepository<DespesaOutrosInsumoServico, Long>{
	public List<DespesaOutrosInsumoServico> findByPropriedade(Propriedade p);
}
