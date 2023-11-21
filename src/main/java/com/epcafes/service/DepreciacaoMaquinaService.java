package com.epcafes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.dto.DepreciacaoMaquinaDTO;
import com.epcafes.dto.DepreciacaoMaquinaTO;
import com.epcafes.exception.BusinessException;
import com.epcafes.model.DepreciacaoMaquina;
import com.epcafes.model.Maquina;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;
import com.epcafes.repository.DepreciacaoMaquinaRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DepreciacaoMaquinaService {
	
	@Autowired
	private DepreciacaoMaquinaRepository depreciacaoMaquinaRepository;
	
	@Autowired
	private MaquinaService maquinaService;
	
	@Autowired
	private TalhaoService talhaoService;
	
	@Transactional
	public DepreciacaoMaquina salvar(DepreciacaoMaquina depreciacaoMaquina) throws BusinessException {
		
		List<DepreciacaoMaquina> depreciacoes =  depreciacaoMaquinaRepository.findAllByMaquina(depreciacaoMaquina.getMaquina());
		
		if(!depreciacoes.isEmpty()) {
			
			for (DepreciacaoMaquina depreciacao : depreciacoes) {
				
				if(depreciacao.getMaquina().getId() == depreciacaoMaquina.getMaquina().getId()						
						&& depreciacao.getId() != depreciacaoMaquina.getId()) {
					
					throw new BusinessException("", "A Maquina " + depreciacao.getMaquina().getNome() +
							" já possui uma depreciação cadastrada");
				}
			}
		}
		
		//calculo da depreciacao = ((ValorBem * (1 - (ValorResidual / 100))) / VidaUtilHoras) * HorasTrabalhadas
		BigDecimal valorResidual = BigDecimal.valueOf(depreciacaoMaquina.getMaquina().getValorResidual());
		
		BigDecimal numerador = depreciacaoMaquina.getMaquina().getValor()
				.multiply(BigDecimal.valueOf(1)
						.subtract(valorResidual.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
						);
		
		BigDecimal denominador = BigDecimal.valueOf( depreciacaoMaquina.getMaquina().getVidaUtilHoras());	
		
		//total = (numerador / denominador)
		BigDecimal total = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
		
		log.info("Numerador = " + numerador);
		log.info("denominador = " + denominador);
		log.info("total = " + total);
		
		depreciacaoMaquina.setValorDepreciacao(total.multiply(depreciacaoMaquina.getHorasTrabalhadas()));
		
		return depreciacaoMaquinaRepository.save(depreciacaoMaquina);
	}
	
	public Optional<DepreciacaoMaquina> buscarPorId(Long id) {
		
		return depreciacaoMaquinaRepository.findById(id);
	}
	
	public List<DepreciacaoMaquina> listarDepreciacoesMaquinasPorTenant(Long tenantId){
		
		return depreciacaoMaquinaRepository.findAllByTenantId(tenantId);
	}
	
	public List<DepreciacaoMaquina> listarDepreciacoesMaquinasPorTenantPagined(Long tenantId, int currPage, int pageSize){
		
		int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.depreciacaoMaquinaRepository.findAllByTenantId(tenantId).size());
		return depreciacaoMaquinaRepository.findAllByTenantId(tenantId).subList(start, end);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		depreciacaoMaquinaRepository.deleteById(id);
	}
	
	/*
	 * Relatório Depreciacao de Maquina
	 */
	 
	public List<DepreciacaoMaquinaTO> buscarDepreciacoesTO(Propriedade propriedade) {
    	
        //lista de DepreciacaoMaquinaTO de cada Maquina
        List<DepreciacaoMaquinaTO> depreciacoesTO = new ArrayList<>();
        
        List<Talhao> talhoes = this.talhaoService.findAllByPropriedade(propriedade);
    	
    	List<Maquina> maquinas = this.maquinaService.buscarPorTenant(propriedade.getTenantId());
    	
    	for(Maquina maquina : maquinas) {
    		
    		List<DepreciacaoMaquinaDTO> depreciacoesDTO = this.depreciacaoMaquinaRepository.buscarDepreciacoesMaquinaDTO(maquina, propriedade.getTenantId());
    		
    		log.info("qde DTO..." + depreciacoesDTO.size());
    		
    		if(depreciacoesDTO.size() > 0) {
    			
    			DepreciacaoMaquinaTO to = new DepreciacaoMaquinaTO();
                
                for(DepreciacaoMaquinaDTO depreciacaoMaquinaDTO : depreciacoesDTO) {
                	
                	to.setNome(depreciacaoMaquinaDTO.getNome());
                	to.setValorBemNovo(depreciacaoMaquinaDTO.getValorBemNovo());
                	to.setValorResidual(depreciacaoMaquinaDTO.getValorResidual());
                	to.setVidaUtilHoras(depreciacaoMaquinaDTO.getVidaUtilHoras());
                	to.setHorasTrabalhadas(depreciacaoMaquinaDTO.getHorasTrabalhadas());
                	to.setValorDepreciacao(depreciacaoMaquinaDTO.getValorDepreciacao());
                	to.setPorcentagemUtilizacao(depreciacaoMaquinaDTO.getPorcentagemUtilizacao());
                }
                
                depreciacoesTO.add(to);
    		}
    	}
    	
    	//Relatorio Parte Por Talhão
    	
    	for(DepreciacaoMaquinaTO depreciacaoTO : depreciacoesTO) {
    		
    		Float soma = 0.0f;
    		
    		for(Talhao talhao : talhoes) {
    			
    			soma+= talhao.getArea();
    		}
    		
    		for(Talhao talhao : talhoes) {
        		
    			//valorTotalLavoura = valorDepreciacao * (porcentagem de utilização / 100)
    			Float porcentagem = depreciacaoTO.getPorcentagemUtilizacao() / 100;
    			depreciacaoTO.setValorTotalLavoura(depreciacaoTO.getValorDepreciacao()
        				.multiply(new BigDecimal(porcentagem))
        				.setScale(2, RoundingMode.HALF_UP));
        		
        		//valorPorTalhao = (valorTotalLavoura * areaTalhao) / somaAreasTalhoes
        		BigDecimal calculo = depreciacaoTO.getValorTotalLavoura()
        				.multiply(new BigDecimal(talhao.getArea()))
        				.divide(new BigDecimal(soma), 2, RoundingMode.HALF_UP);
        		
        		calculo = calculo.setScale(2, RoundingMode.HALF_UP); //arredondando para 2 casas decimais
        		depreciacaoTO.getValoresPorTalhao().add(calculo);
    		}
    	}

        return depreciacoesTO;
    }
}
