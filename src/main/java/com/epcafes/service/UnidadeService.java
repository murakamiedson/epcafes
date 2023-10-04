package com.epcafes.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.epcafes.model.Unidade;

import com.epcafes.repository.UnidadeRepository;

@Service
public class UnidadeService implements Serializable{
	
	@Autowired
	UnidadeRepository repository;

	public List<Unidade> findByTenantId(Long tenantId){
		return repository.findByTenantId(tenantId);
	}

}
