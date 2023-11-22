package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Propriedade;
import com.epcafes.repository.PropriedadeRepository;

@Service
public class PropriedadeService {
	
	@Autowired
	PropriedadeRepository repository;
	
	public List<Propriedade> findByTenantId(Long tenantId){
		return repository.findByTenantId(tenantId);
	}
	
	public List<Propriedade> findAll() {
		return this.repository.findAll();
	}
	
	public List<Propriedade> findPaginated(int currPage, int pageSize) {
        int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, repository.findAll().size());
        return repository.findAll().subList(start, end);
    }
	public Optional<Propriedade> findById(Long id){
		return repository.findById(id);
	}
	
	public Propriedade getById(Long id){
		return repository.findById(id).orElse(null);
	}
	
	public void salvar (Propriedade propriedade){
		repository.save(propriedade);
	}
	
	public void excluir(Propriedade propriedade){
		repository.delete(propriedade);
	}

}
