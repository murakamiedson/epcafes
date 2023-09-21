package com.epcafes.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epcafes.model.DespesaInsumoServico;
import com.epcafes.model.repository.DespesaInsumoServicoRepository;

public class DespesaInsumoServicoService implements Serializable{

	@Autowired
	DespesaInsumoServicoRepository repository;

	public List<DespesaInsumoServico> getAll(Long tenantId){
			return repository.findAllbyTenantId(tenantId);
	}

	public void delete(DespesaInsumoServico despesa){
		repository.delete(despesa);
	}

	public void save(DespesaInsumoServico despesa){
		repository.save(despesa);
	}
}

