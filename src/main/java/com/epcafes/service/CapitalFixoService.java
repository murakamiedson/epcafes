package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.model.CapitalFixo;
import com.epcafes.repository.CapitalFixoRepository;

@Service
public class CapitalFixoService {
	
	@Autowired
	private CapitalFixoRepository capitalFixoRepository;
	
	@Transactional
	public CapitalFixo salvar(CapitalFixo capitalFixo) {
		
		return capitalFixoRepository.save(capitalFixo);
	}
	
	public Optional<CapitalFixo> buscar(Long id) {
		
		return capitalFixoRepository.findById(id);
	}
	
	public List<CapitalFixo> listarCapitalFixo(){
		
		return capitalFixoRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		
		capitalFixoRepository.deleteById(id);
	}
}
