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
import com.epcafes.util.NegocioExeption;


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
	public String adicionarTalhao(Talhao talhao) throws NegocioExeption {
		talhao.setTenant_id(1L);

    	Propriedade propriedadeTeste = new Propriedade();
    	propriedadeTeste.setId(1L);
    	talhao.setPropriedade(propriedadeTeste);
		talhaoService.salvar(talhao);
		return "redirect:/talhao";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletarTalhao(@PathVariable(value = "id") Long id) throws NegocioExeption {
		
		talhaoService.excluir(id);
		return "redirect:/talhao";
	}
	
	@PostMapping("/atualizar")
	public String atualizarTalhao(Talhao talhao) {
		talhaoService.salvar(talhao); 
		return "redirect:/talhao";
	}
}