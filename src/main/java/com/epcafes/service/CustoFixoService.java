package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.CustoFixo;
import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.repository.CustoFixoRepository;

@Service
public class CustoFixoService {
	
	@Autowired
	private CustoFixoRepository custoFixoRepository;
	
	@Autowired
	private DespesaCustoFixoService despesaCustoFixoService;
	
	@Transactional
	public CustoFixo salvar(CustoFixo custoFixo) {
		
		return custoFixoRepository.save(custoFixo);
	}
	
	public Optional<CustoFixo> buscarPorId(Long id) {
		
		return custoFixoRepository.findById(id);
	}
	
	public List<CustoFixo> listarCustosFixos(){
		
		return custoFixoRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) throws BusinessException {
		
		Optional<CustoFixo> custoFixo = custoFixoRepository.findById(id);
		
		List<DespesaCustoFixo> despesas = despesaCustoFixoService.listarDespesasDeUmCustoFixo(custoFixo.get());
		
		if(!despesas.isEmpty())
			throw new BusinessException("", "Não é possivel excluir o Custo Fixo " + custoFixo.get().getNome() + " pois possui despesas cadastradas!");
		
		custoFixoRepository.deleteById(id);
	}
}
