package com.epcafes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.DepreciacaoMaquina;
import com.epcafes.repository.DepreciacaoMaquinaRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DepreciacaoMaquinaService {
	
	@Autowired
	private DepreciacaoMaquinaRepository depreciacaoMaquinaRepository;
	
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
}
