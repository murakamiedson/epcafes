package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Talhao;
import com.epcafes.repository.TalhaoRepository;

@Service
public class TalhaoService {
	
	@Autowired
	private TalhaoRepository talhaoRepository;
	
	public List<Talhao> findAll() {
		return talhaoRepository.findAll();
	}
	
	public void salvar(Talhao talhao) {
		talhaoRepository.save(talhao);
	}
	
	public void excluir(Long id){
		talhaoRepository.deleteById(id);
	}
	

}