package com.epcafes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epcafes.model.Instalacao;
import com.epcafes.repository.InstalacaoRepository;

@Service
public class InstalacaoService {
	InstalacaoRepository repository;
	
	public List<Instalacao> findAll() {
		return repository.findAll();
	}
	
	public void salvar(Instalacao instalacao) {
		repository.save(instalacao);
	}
	
	public void excluir(Long id){
		repository.deleteById(id);
	}
}
