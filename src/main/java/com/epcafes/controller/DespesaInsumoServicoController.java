package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epcafes.model.DespesaInsumoServico;
import com.epcafes.service.DespesaInsumoServicoService;

@Controller
public class DespesaInsumoServicoController{

	@Autowired
	DespesaInsumoServicoService service;

	@GetMapping("/despesa_insumo_servico")
	public ModelAndView despesaInsumoServico(){
		ModelAndView mev = new ModelAndView();

		List<DespesaInsumoServico> despesas = service.getAll(1l); //TODO: PUXAR TENANTID DE UM LOGIN
		
		mev.setViewName("despesa_insumo_servico");

		mev.addObject("despesas", despesas);		
		
		return mev;
	}
}
