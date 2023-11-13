package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.DespesaOutrosInsumoServico;
import com.epcafes.model.Propriedade;
import com.epcafes.repository.DespesaOutrosInsumoServicoRepository;

@Service
public class DespesaOutrosInsumoServicoService {

	@Autowired
	DespesaOutrosInsumoServicoRepository repository;
	
	public DespesaOutrosInsumoServico find(Long id) {
		Optional<DespesaOutrosInsumoServico> dis = repository.findById(id);
		if(dis.isPresent()) {
			return dis.get();
		}
		else return null;
	}
	
	public List<DespesaOutrosInsumoServico> getAll(Propriedade p){
			return repository.findByPropriedade(p);
	}

	public void deleteById(Long id){
		repository.deleteById(id);
	}

	public DespesaOutrosInsumoServico save(DespesaOutrosInsumoServico despesa){
		return repository.save(despesa);
	}
}

