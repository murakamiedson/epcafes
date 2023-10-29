package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.repository.DespesaCustoFixoRepository;

@Service
public class DespesaCustoFixoService {
	
	@Autowired
	private DespesaCustoFixoRepository despesaCustoFixoRepository;
	
	@Transactional
	public DespesaCustoFixo salvar(DespesaCustoFixo despesa) {
		
		return despesaCustoFixoRepository.save(despesa);
	}
	
	public Optional<DespesaCustoFixo> buscarPorId(Long id) {
		
		return despesaCustoFixoRepository.findById(id);
	}
	
	public List<DespesaCustoFixo> listarDespesasCustosFixos(){
		
		return despesaCustoFixoRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		
		despesaCustoFixoRepository.deleteById(id);
	}
}
