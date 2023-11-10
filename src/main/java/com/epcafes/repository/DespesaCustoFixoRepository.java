package com.epcafes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epcafes.model.CustoFixo;
import com.epcafes.model.DespesaCustoFixo;

public interface DespesaCustoFixoRepository extends JpaRepository<DespesaCustoFixo, Long> {
	
	//retorna todas as despesas de um determinado custoFixo
	List<DespesaCustoFixo> findAllByCustoFixo(CustoFixo custoFixo);
}
