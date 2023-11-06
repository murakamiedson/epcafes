package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epcafes.model.DespesaOutrosInsumoServico;
import com.epcafes.model.Usuario;
import com.epcafes.service.DespesaOutrosInsumoServicoService;
import com.epcafes.service.PropriedadeService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/despesa_insumo_servico")
@Log4j2
public class DespesaOutrosInsumoServicoController{

	@Autowired
	DespesaOutrosInsumoServicoService service;
	
	@Autowired
	PropriedadeService propService;

	@GetMapping
	public ModelAndView despesaInsumoServico(Authentication auth){
		ModelAndView mev = new ModelAndView();

		Usuario userLogado = (Usuario) auth.getPrincipal();

		List<DespesaOutrosInsumoServico> despesas = service.getAll(userLogado.getTenant().getId()); 
		
		mev.setViewName("restricted/custo/DespesaOutrosInsumoServico");

		mev.addObject("despesas", despesas);		
		
		return mev;
	}
	
	@PostMapping("/create")
	public void create(@RequestBody DespesaOutrosInsumoServico data, Authentication auth) {
		Usuario userLogado = (Usuario) auth.getPrincipal();
		data.setId(null);
		data.setPropriedade(propService.findByTenantId(userLogado.getTenant().getId()).get(0));
		data.setTenantId(userLogado.getTenant().getId()); 
		service.save(data);
	}
	
	@PostMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		service.deleteById(id);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody DespesaOutrosInsumoServico despesa_nova) {
		DespesaOutrosInsumoServico despesa = service.find(despesa_nova.getId());
		despesa.setDescricao(despesa_nova.getDescricao());
		despesa.setNotaFiscal(despesa_nova.getNotaFiscal());
		despesa.setValor(despesa_nova.getValor());
		despesa.setPorcUtilizacao(despesa_nova.getPorcUtilizacao());
		service.save(despesa);
		log.debug(despesa);
	}
}
