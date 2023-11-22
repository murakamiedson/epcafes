package com.epcafes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.dto.DepreciacaoInstalacaoDTO;
import com.epcafes.dto.DepreciacaoInstalacaoTO;
import com.epcafes.exception.BusinessException;
import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Instalacao;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;
import com.epcafes.repository.DepreciacaoInstalacaoRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DepreciacaoInstalacaoService {
	
	@Autowired
	private DepreciacaoInstalacaoRepository depreciacaoInstalacaoRepository;
	
	@Autowired
	private InstalacaoService instalacaoService;
	
	@Autowired
	private TalhaoService talhaoService;
	
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
	
	/*
	 * Relatório Depreciacao de Instalacao
	 */
	 
	public List<DepreciacaoInstalacaoTO> buscarDepreciacoesTO(Propriedade propriedade) {
    	
        //lista de DepreciacaoInstalacaoTO de cada Instalacao
        List<DepreciacaoInstalacaoTO> depreciacoesTO = new ArrayList<>();
        
        List<Talhao> talhoes = this.talhaoService.findAllByPropriedade(propriedade);
    	
    	List<Instalacao> instalacoes = this.instalacaoService.findAllByPropriedade(propriedade);
    	
    	for(Instalacao instalacao : instalacoes) {
    		
    		List<DepreciacaoInstalacaoDTO> depreciacoesDTO = this.depreciacaoInstalacaoRepository.buscarDepreciacoesInstalacaoDTO(instalacao, propriedade);
    		
    		log.info("qde DTO..." + depreciacoesDTO.size());
    		
    		if(depreciacoesDTO.size() > 0) {
    			
    			DepreciacaoInstalacaoTO to = new DepreciacaoInstalacaoTO();
                
                for(DepreciacaoInstalacaoDTO depreciacaoInstalacaoDTO : depreciacoesDTO) {
                	
                	to.setNome(depreciacaoInstalacaoDTO.getNome());
                	to.setValorBemNovo(depreciacaoInstalacaoDTO.getValorBemNovo());
                	to.setValorResidual(depreciacaoInstalacaoDTO.getValorResidual());
                	to.setVidaUtilAnos(depreciacaoInstalacaoDTO.getVidaUtilAnos());
                	to.setPorcentagemUtilizacao(depreciacaoInstalacaoDTO.getPorcentagemUtilizacao());
                	to.setValorDepreciacao(depreciacaoInstalacaoDTO.getValorDepreciacao());
                }
                
                depreciacoesTO.add(to);
    		}
    	}
    	
    	//Relatorio Parte Por Talhão
    	
    	for(DepreciacaoInstalacaoTO depreciacaoTO : depreciacoesTO) {
    		
    		Float soma = 0.0f;
    		
    		for(Talhao talhao : talhoes) {
    			
    			soma+= talhao.getArea();
    		}
    		
    		for(Talhao talhao : talhoes) {
        		
        		//valorPorTalhao = (valorDepreciacao * areaTalhao) / somaAreasTalhoes
        		BigDecimal calculo = depreciacaoTO.getValorDepreciacao()
        				.multiply(new BigDecimal(talhao.getArea()))
        				.divide(new BigDecimal(soma), 2, RoundingMode.HALF_UP);
        		
        		calculo = calculo.setScale(2, RoundingMode.HALF_UP); //arredondando para 2 casas decimais
        		depreciacaoTO.getValoresPorTalhao().add(calculo);
    		}
    	}

        return depreciacoesTO;
    }
}
