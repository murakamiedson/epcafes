package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Propriedade;
import com.epcafes.repository.DepreciacaoInstalacaoRepository;

@Service
public class DepreciacaoInstalacaoService {
	
	@Autowired
	private DepreciacaoInstalacaoRepository depreciacaoInstalacaoRepository;
	
	@Transactional
	public DepreciacaoInstalacao salvar(DepreciacaoInstalacao depreciacaoInstalacao) {
		
		return depreciacaoInstalacaoRepository.save(depreciacaoInstalacao);
	}
	
	public Optional<DepreciacaoInstalacao> buscarPorId(Long id) {
		
		return depreciacaoInstalacaoRepository.findById(id);
	}
	
	public List<DepreciacaoInstalacao> listarDepreciacoesInstalacoes(){
		
		return depreciacaoInstalacaoRepository.findAll();
	}
	
	public List<DepreciacaoInstalacao> listarDepreciacoesInstalacoesPorPropriedade(Propriedade propriedade){
		
		return depreciacaoInstalacaoRepository.findAllByPropriedade(propriedade);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		depreciacaoInstalacaoRepository.deleteById(id);
	}
}
