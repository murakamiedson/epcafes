package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.DespesaInsumoServico;
import com.epcafes.repository.DespesaInsumoServicoRepository;

@Service
public class DespesaInsumoServicoService {

	@Autowired
	DespesaInsumoServicoRepository repository;

	public List<DespesaInsumoServico> getAll(Long tenantId){
			return repository.findByTenantId(tenantId);
	}

	public void delete(DespesaInsumoServico despesa){
		repository.delete(despesa);
	}

	public void save(DespesaInsumoServico despesa){
		repository.save(despesa);
	}
}

