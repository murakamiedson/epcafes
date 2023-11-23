package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.model.CapitalFixo;
import com.epcafes.model.Cultivo;
import com.epcafes.repository.CultivoRepository;

@Service
public class CultivoService {
	
	@Autowired
	private CultivoRepository cultivoRepository;
	
	@Transactional
	public Cultivo salvar(Cultivo cultivo) {
		
		cultivo.setRemuneracao(this.calculaRemuneracaoCultivo(cultivo));
		
		return cultivoRepository.save(cultivo);
	}
	
	public Optional<Cultivo> buscar(Long id) {		
	
		return cultivoRepository.findById(id);
	}
	
	public List<Cultivo> listarCultivo(){
		
		return cultivoRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		
		cultivoRepository.deleteById(id);
	}
	
	private double calculaRemuneracaoCultivo(Cultivo rc) {
		return (rc.getExaustaoCultivo()/2)*rc.getTaxaPoupanca();
	}
	
	public Optional<Cultivo> buscarPorId(Long id) {
		
		return cultivoRepository.findById(id);
	}
	
	public List<Cultivo> listarCultivosPagined(int currPage, int pageSize){
		
		int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.cultivoRepository.findAll().size());
		return cultivoRepository.findAll().subList(start, end);
	}
}
