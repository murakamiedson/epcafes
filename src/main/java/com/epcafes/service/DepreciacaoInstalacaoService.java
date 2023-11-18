package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Propriedade;
import com.epcafes.repository.DepreciacaoInstalacaoRepository;

@Service
public class DepreciacaoInstalacaoService {
	
	@Autowired
	private DepreciacaoInstalacaoRepository depreciacaoInstalacaoRepository;
	
	@Transactional
	public DepreciacaoInstalacao salvar(DepreciacaoInstalacao depreciacaoInstalacao) throws BusinessException {
		
		List<DepreciacaoInstalacao> depreciacoes =  depreciacaoInstalacaoRepository.findAllByInstalacao(depreciacaoInstalacao.getInstalacao());
		
		if(!depreciacoes.isEmpty()) {
			
			for (DepreciacaoInstalacao depreciacao : depreciacoes) {
				
				if(depreciacao.getInstalacao().getId() == depreciacaoInstalacao.getInstalacao().getId()						
						&& depreciacao.getId() != depreciacaoInstalacao.getId()) {
					
					throw new BusinessException("", "A instalação " + depreciacao.getInstalacao().getNome() +
							" já possui uma depreciação cadastrada");
				}
			}
		}
		
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
	
	public List<DepreciacaoInstalacao> listarDepreciacoesInstalacoesPorPropriedadePagined(Propriedade propriedade, int currPage, int pageSize){
		
		int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.depreciacaoInstalacaoRepository.findAllByPropriedade(propriedade).size());
		return depreciacaoInstalacaoRepository.findAllByPropriedade(propriedade).subList(start, end);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		depreciacaoInstalacaoRepository.deleteById(id);
	}
}
