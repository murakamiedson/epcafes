package com.epcafes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Propriedade;
import com.epcafes.repository.DepreciacaoInstalacaoRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
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
		
		//calculo da depreciacao = ((ValorBem * (1 - (ValorResidual / 100))) / VidaUtilAnos) * PorcentagemUtilizacao
		BigDecimal valorResidual = BigDecimal.valueOf(depreciacaoInstalacao.getInstalacao().getValorResidual());
		
		BigDecimal numerador = BigDecimal.valueOf(depreciacaoInstalacao.getInstalacao().getValor())
				.multiply(BigDecimal.valueOf(1)
						.subtract(valorResidual.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
						);
		
		BigDecimal denominador = BigDecimal.valueOf(depreciacaoInstalacao.getInstalacao().getVidaUtilAnos());	
		
		//total = (numerador / denominador)
		BigDecimal total = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
		
		log.info("Numerador = " + numerador);
		log.info("denominador = " + denominador);
		log.info("total = " + total);
		
		depreciacaoInstalacao.setValorDepreciacao(total.multiply(BigDecimal.valueOf(depreciacaoInstalacao.getPorcentagemUtilizacao() / 100)));
		
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
