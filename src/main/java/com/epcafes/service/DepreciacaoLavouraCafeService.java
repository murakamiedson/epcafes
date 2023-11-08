package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.model.DepreciacaoLavouraCafe;
import com.epcafes.repository.DepreciacaoLavouraCafeRepository;

@Service
public class DepreciacaoLavouraCafeService {
	
	@Autowired
	private DepreciacaoLavouraCafeRepository depreciacaoLavouraCafeRepository;
	
	@Transactional
	public DepreciacaoLavouraCafe salvar(DepreciacaoLavouraCafe depreciacaoLavouraCafe) {
		
		return depreciacaoLavouraCafeRepository.save(depreciacaoLavouraCafe);
	}
	
	public Optional<DepreciacaoLavouraCafe> buscarPorId(Long id) {
		
		return depreciacaoLavouraCafeRepository.findById(id);
	}
	
	public List<DepreciacaoLavouraCafe> listarDepreciacoesLavourasCafe(){
		
		return depreciacaoLavouraCafeRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		
		depreciacaoLavouraCafeRepository.deleteById(id);
	}
}
