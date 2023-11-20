package com.epcafes.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Propriedade;
import com.epcafes.repository.PropriedadeRepository;

@Service
public class PropriedadeService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	PropriedadeRepository repository;
	
	public List<Propriedade> findByTenantId(Long tenantId){
		return repository.findByTenantId(tenantId);
	}

	public Optional<Propriedade> findById(Long id){
		return repository.findById(id);
	}
	
	public void salvar (Propriedade propriedade){
		repository.save(propriedade);
	}
	
	public void excluir(Long id){
		repository.deleteById(id);
	}
}
