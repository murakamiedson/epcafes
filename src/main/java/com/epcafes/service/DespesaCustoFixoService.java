package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.CustoFixo;
import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.repository.DespesaCustoFixoRepository;

@Service
public class DespesaCustoFixoService {
	
	@Autowired
	private DespesaCustoFixoRepository despesaCustoFixoRepository;
	
	@Transactional
	public DespesaCustoFixo salvar(DespesaCustoFixo despesaCustoFixo) throws BusinessException {
		
		List<DespesaCustoFixo> despesas = despesaCustoFixoRepository.findAllByCustoFixo(despesaCustoFixo.getCustoFixo());
		
		if(!despesas.isEmpty()) {
			
			for (DespesaCustoFixo despesa : despesas) {
				
				if(despesa.getMesAno().getMonth() == despesaCustoFixo.getMesAno().getMonth()
						&& despesa.getMesAno().getYear() == despesaCustoFixo.getMesAno().getYear()
						&& despesa.getId() != despesaCustoFixo.getId()) {
					
					throw new BusinessException("", "O CustoFixo " + despesa.getCustoFixo().getNome() +
							" já possui uma despesa em " + 
							despesa.getMesAno().getMonth().getValue() + "/" + despesa.getMesAno().getYear());
				}
			}
		}
	
		return despesaCustoFixoRepository.save(despesaCustoFixo);
	}
	
	public Optional<DespesaCustoFixo> buscarPorId(Long id) {
		
		return despesaCustoFixoRepository.findById(id);
	}
	
	public List<DespesaCustoFixo> listarDespesasCustosFixos(){
		
		return despesaCustoFixoRepository.findAll();
	}
	
	public List<DespesaCustoFixo> listarDespesasDeUmCustoFixo(CustoFixo custoFixo){
		
		return despesaCustoFixoRepository.findAllByCustoFixo(custoFixo);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		despesaCustoFixoRepository.deleteById(id);
	}
}
