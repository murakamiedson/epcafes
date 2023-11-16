package com.epcafes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.model.Instalacao;
import com.epcafes.model.Propriedade;
import com.epcafes.repository.InstalacaoRepository;

@Service
public class InstalacaoService {
	@Autowired
	InstalacaoRepository repository;
	
	public List<Instalacao> findAll() {
		return repository.findAll();
	}
	
	public List<Instalacao> listarInstalacoesPorPropriedade(Propriedade propriedade){
		
		return repository.findAllByPropriedade(propriedade);
	}
	
	public void salvar(Instalacao instalacao) {
		repository.save(instalacao);
	}
	
	public void excluir(Long id){
		repository.deleteById(id);
	}
}
