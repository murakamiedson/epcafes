package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.DespesaOutrosInsumoServico;
import com.epcafes.repository.DespesaInsumoServicoRepository;

@Service
public class DespesaOutrosInsumoServicoService {

	@Autowired
	DespesaInsumoServicoRepository repository;
	
	public DespesaOutrosInsumoServico find(Long id) {
		return repository.findById(id).get();
	}
	
	public List<DespesaOutrosInsumoServico> getAll(Long tenantId){
			return repository.findByTenantId(tenantId);
	}

	public void deleteById(Long id){
		repository.deleteById(id);
	}

	public void save(DespesaOutrosInsumoServico despesa){
		repository.save(despesa);
	}
}

