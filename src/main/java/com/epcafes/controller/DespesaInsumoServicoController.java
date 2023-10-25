package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epcafes.model.DespesaInsumoServico;
import com.epcafes.service.DespesaInsumoServicoService;
import com.epcafes.service.PropriedadeService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/despesa_insumo_servico")
@Log4j2
public class DespesaInsumoServicoController{

	@Autowired
	DespesaInsumoServicoService service;
	
	@Autowired
	PropriedadeService propService;

	@GetMapping
	public ModelAndView despesaInsumoServico(){
		ModelAndView mev = new ModelAndView();

		List<DespesaInsumoServico> despesas = service.getAll(1l); //TODO: PUXAR TENANTID DE UM LOGIN
		
		mev.setViewName("restricted/custo/DespesaInsumoServico");

		mev.addObject("despesas", despesas);		
		
		return mev;
	}
	
	@PostMapping("/create")
	public void create(@RequestBody DespesaInsumoServico data) {
		data.setId(null);
		data.setPropriedade(propService.findByTenantId(1L).get(0));
		data.setTenantId(1L); 
		service.save(data);
	}
	
	@PostMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		service.deleteById(id);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody DespesaInsumoServico despesa_nova) {
		DespesaInsumoServico despesa = service.find(despesa_nova.getId());
		despesa.setDescricao(despesa_nova.getDescricao());
		despesa.setNotaFiscal(despesa_nova.getNotaFiscal());
		despesa.setValor(despesa_nova.getValor());
		despesa.setPorcUtilizacao(despesa_nova.getPorcUtilizacao());
		service.save(despesa);
		log.debug(despesa);
	}
}
