package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epcafes.model.DespesaOutrosInsumoServico;
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
	
	public List<DespesaOutrosInsumoServico> findAll(){
			return repository.findAll();
	}

	public void deleteById(Long id){
		repository.deleteById(id);
	}

	public DespesaOutrosInsumoServico save(DespesaOutrosInsumoServico despesa){
		return repository.save(despesa);
	}
	
	 public List<DespesaOutrosInsumoServico> findPaginated(int currPage, int pageSize) {
	        Pageable pageable = PageRequest.of(currPage - 1, pageSize);
	        return this.repository.findPaginated(pageable).getContent();
	 }
	 
}

