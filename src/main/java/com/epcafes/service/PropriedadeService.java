package com.epcafes.service;

import java.io.Serializable;
import java.util.List;

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

}
