package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.Propriedade;
import com.epcafes.model.Talhao;
import com.epcafes.service.TalhaoService;


@Controller
@RequestMapping("/talhao")
public class TalhaoController {
	
	@Autowired
	private TalhaoService talhaoService;
	
	@GetMapping
	public String talhaoPage(Model model) {
		
		List<Talhao> talhoes = talhaoService.findAll();
		model.addAttribute("talhoes", talhoes);
		
		return "restricted/cadastro/ManterTalhao"; 
	}
	
	@GetMapping("/adicionar")
	public String adicionarTalhaoForm() {
		return "restricted/cadastro/ManterTalhao";
	}
	
	
	@PostMapping("/adicionar")
	public String adicionarTalhao(Talhao talhao) {
		
		Propriedade propriedade = new Propriedade();
		propriedade.setId(1L);
		talhao.setPropriedade(propriedade);
		talhao.setTenant_id(1L);
		talhaoService.addTalhao(talhao);
		
		return "redirect:/talhao";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletarTalhao(@PathVariable(value = "id") Long id) {
		
		talhaoService.deleteTalhao(id);
		return "redirect:/talhao";
	}
	
	@GetMapping("/atualizar/{id}")
	public String showAtualizarTalhao(@PathVariable(value = "id" ) Long id, Model model) {
		Talhao talhao = talhaoService.get(id);
		model.addAttribute("talhao", talhao);
		return "redirect:/talhao";
	}
	
	@PostMapping("/atualizar")
	public String atualizarTalhao(Talhao talhao) {
		talhaoService.addTalhao(talhao); 
		return "redirect:/talhao";
	}
}