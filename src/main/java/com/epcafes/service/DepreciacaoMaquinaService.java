package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.DepreciacaoMaquina;
import com.epcafes.repository.DepreciacaoMaquinaRepository;

@Service
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
