package com.epcafes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.model.TerraPropria;
import com.epcafes.repository.TerraPropriaRepository;

@Service
public class TerraPropriaService {
	
	@Autowired
	private TerraPropriaRepository terraPropriaRepository;
	
	@Transactional
	public TerraPropria salvar(TerraPropria terraPropria) {
		
		terraPropria.setRemuneracao(this.calculaRemuneracaoTerraPropria(terraPropria));
		
		return terraPropriaRepository.save(terraPropria);
	}
	
	public Optional<TerraPropria> buscar(Long id) {		
	
		return terraPropriaRepository.findById(id);
	}
	
	public List<TerraPropria> listarTerraPropria(){
		
		return terraPropriaRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		
		terraPropriaRepository.deleteById(id);
	}
	
	private double calculaRemuneracaoTerraPropria(TerraPropria tp) {
		return ((tp.getValorTerraNua()*tp.getTaxaPoupanca()/2)/tp.getSafrasAnos())*tp.getPercTerraPropria();
	}
	
	public Optional<TerraPropria> buscarPorId(Long id) {
		
		return terraPropriaRepository.findById(id);
	}
	
	public List<TerraPropria> listarTerraPropriaPagined(int currPage, int pageSize){
		
		int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.terraPropriaRepository.findAll().size());
		return terraPropriaRepository.findAll().subList(start, end);
	}
}

