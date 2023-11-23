package com.epcafes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.dto.DepreciacaoLavouraCafeTO;
import com.epcafes.model.DepreciacaoLavouraCafe;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;
import com.epcafes.repository.DepreciacaoLavouraCafeRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DepreciacaoLavouraCafeService {
	
	@Autowired
	private DepreciacaoLavouraCafeRepository depreciacaoLavouraCafeRepository;
	
	@Autowired
	private TalhaoService talhaoService;
	
	@Transactional
	public DepreciacaoLavouraCafe salvar(DepreciacaoLavouraCafe depreciacaoLavouraCafe) {
		
		//calculo da depreciacao = (CustoImplantacao - ReceitasObtidas) / VidaUtilAnos
		BigDecimal numerador = depreciacaoLavouraCafe.getCustoImplantacao()
									.subtract(depreciacaoLavouraCafe.getReceitasObtidas());
		
		BigDecimal denominador = BigDecimal.valueOf(depreciacaoLavouraCafe.getVidaUtilAnos());	
		
		//total = (numerador / denominador)
		BigDecimal total = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
		
		log.info("Numerador = " + numerador);
		log.info("denominador = " + denominador);
		log.info("total = " + total);
		
		depreciacaoLavouraCafe.setValorDepreciacao(total);
		
		return depreciacaoLavouraCafeRepository.save(depreciacaoLavouraCafe);
	}
	
	public Optional<DepreciacaoLavouraCafe> buscarPorId(Long id) {
		
		return depreciacaoLavouraCafeRepository.findById(id);
	}
	
	public List<DepreciacaoLavouraCafe> listarDepreciacoesLavourasCafe(){
		
		return depreciacaoLavouraCafeRepository.findAll();
	}
	
	public List<DepreciacaoLavouraCafe> listarDepreciacoesLavourasCafePorPropriedade(Propriedade propriedade){
		
		return depreciacaoLavouraCafeRepository.findAllByPropriedade(propriedade);
	}
	
	public List<DepreciacaoLavouraCafe> listarDepreciacoesLavourasCafePorPropriedadePagined(Propriedade propriedade, int currPage, int pageSize){
		
		int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.depreciacaoLavouraCafeRepository.findAllByPropriedade(propriedade).size());
		return depreciacaoLavouraCafeRepository.findAllByPropriedade(propriedade).subList(start, end);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		depreciacaoLavouraCafeRepository.deleteById(id);
	}
	
	/*
	 * Relatório Depreciacao de Lavoura de Café
	 */
	 
	public List<DepreciacaoLavouraCafeTO> buscarDepreciacoesTO(Propriedade propriedade) {
		
		//lista de DepreciacaoLavouraCafeTO de cada DepreciacaoLavouraCafe
        List<DepreciacaoLavouraCafeTO> depreciacoesTO = new ArrayList<>();
        
        List<Talhao> talhoes = this.talhaoService.findAllByPropriedade(propriedade);
    	
    	List<DepreciacaoLavouraCafe> depreciacoes = this.depreciacaoLavouraCafeRepository.findAllByPropriedade(propriedade);
    		
    	log.info("qde DTO..." + depreciacoes.size());
    		
    	if(depreciacoes.size() > 0) {
    			  
            for(DepreciacaoLavouraCafe depreciacaoLavouraCafe : depreciacoes) {
                	
            	DepreciacaoLavouraCafeTO to = new DepreciacaoLavouraCafeTO();
            	
            	to.setDescricao(depreciacaoLavouraCafe.getDescricao());
            	to.setCustoImplantacao(depreciacaoLavouraCafe.getCustoImplantacao());
            	to.setReceitasObtidas(depreciacaoLavouraCafe.getReceitasObtidas());
            	to.setVidaUtilAnos(depreciacaoLavouraCafe.getVidaUtilAnos());
            	to.setValorDepreciacao(depreciacaoLavouraCafe.getValorDepreciacao());	
            	
            	depreciacoesTO.add(to);
            }
		}
    	
    	//Relatorio Parte Por Talhão
    	
    	for(DepreciacaoLavouraCafeTO depreciacaoTO : depreciacoesTO) {
    		
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
