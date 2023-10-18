package com.epcafes.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		return "restricted/cadastro/PesquisaTalhao"; 
	}
	
	@GetMapping("/adicionar")
	public String adicionarTalhaoForm() {
		return "restricted/cadastro/CadastroTalhao";
	}
	
	
	@PostMapping("/adicionar")
	public String adicionarTalhao(Talhao talhao) {
		
		talhaoService.addTalhao(talhao);
		
		return "redirect:/talhao";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletarTalhao(@PathVariable(value = "id") Long id) {
		
		talhaoService.deleteTalhao(id);
		return "redirect:/talhao";
	}
	
	@PostMapping("/atualizar")
	public String atualizarTalhao(Talhao talhao) {
		talhao.setTenant_id(new Random().nextLong(0,10));
		talhaoService.addTalhao(talhao); 
		return "redirect:/talhao";
	}
}