package com.epcafes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.entities.CustoFixo;
import com.epcafes.repositories.CustoFixoRepository;

@Service
public class CustoFixoService {
	
	@Autowired
	private CustoFixoRepository custoFixoRepository;
	
	@Transactional
	public CustoFixo salvar(CustoFixo custoFixo) {
		
		return custoFixoRepository.save(custoFixo);
	}
	
	public List<CustoFixo> listarCustosFixos(){
		
		return custoFixoRepository.findAll();
	}
}
