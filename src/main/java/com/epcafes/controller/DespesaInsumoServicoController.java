package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epcafes.model.DespesaInsumoServico;
import com.epcafes.service.DespesaInsumoServicoService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/despesa_insumo_servico")
@Log4j2
public class DespesaInsumoServicoController{

	@Autowired
	DespesaInsumoServicoService service;

	@GetMapping
	public ModelAndView despesaInsumoServico(){
		ModelAndView mev = new ModelAndView();

		List<DespesaInsumoServico> despesas = service.getAll(1l); //TODO: PUXAR TENANTID DE UM LOGIN
		
		mev.setViewName("restricted/custo/DespesaInsumoServico");

		mev.addObject("despesas", despesas);		
		
		return mev;
	}
	
	@PostMapping("/delete")
	public String delete(Model model, DespesaInsumoServico despesa) {
		service.delete(despesa);
		log.debug(despesa);
		return "redirect:/despesa_insumo_servico";
	}
}
