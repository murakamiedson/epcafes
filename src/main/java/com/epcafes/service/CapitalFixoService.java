package com.epcafes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epcafes.model.CapitalFixo;
import com.epcafes.repository.CapitalFixoRepository;

@Service
public class CapitalFixoService {
	
	@Autowired
	private CapitalFixoRepository capitalFixoRepository;
	
	@Transactional
	public CapitalFixo salvar(CapitalFixo capitalFixo) {
		
		capitalFixo.setRemuneracao(this.calculaRemuneracao(capitalFixo));
		
		return capitalFixoRepository.save(capitalFixo);
	}
	
	public Optional<CapitalFixo> buscar(Long id) {		
	
		return capitalFixoRepository.findById(id);
	}
	
	public List<CapitalFixo> listarCapitalFixo(){
		
		return capitalFixoRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		
		capitalFixoRepository.deleteById(id);
	}
	
	private double calculaRemuneracao(CapitalFixo cf) {
		return (((cf.getValorBemNovo()/2))*cf.getTaxaPoupanca())/(cf.getVidaHoras()*cf.getVidaAnos()) * cf.getHorasTrabalhadas();
	}
	
	public Optional<CapitalFixo> buscarPorId(Long id) {
		
		return capitalFixoRepository.findById(id);
	}
	
	public List<CapitalFixo> listarCapitaisFixosPagined(int currPage, int pageSize){
		
		int start = (currPage - 1) * pageSize;
        int end = Math.min(start + pageSize, this.capitalFixoRepository.findAll().size());
		return capitalFixoRepository.findAll().subList(start, end);
	}
	
	public double getValorBemNovoDespesa(Optional<LocalDate> mesAnoStart, Optional<LocalDate> mesAnoEnd) {
		return capitalFixoRepository.buscarValorBemNovo(mesAnoStart, mesAnoEnd);
	}
}
