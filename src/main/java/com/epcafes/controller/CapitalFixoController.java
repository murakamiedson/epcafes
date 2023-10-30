package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.CapitalFixo;
import com.epcafes.service.CapitalFixoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/capitalFixo")
public class CapitalFixoController {
	
	@Autowired
    private CapitalFixoService capitalFixoService;
    
    @GetMapping
    public String listarCustosFixos(Model model) {
    	
        model.addAttribute("listaCapitaisFixos", capitalFixoService.listarCapitalFixo());
        model.addAttribute("newCapitalFixo", new CapitalFixo());
        return "restricted/custo/CapitalFixo";
    }
    
    @GetMapping("/cadastroTeste")
    public String mostrar(Model model) {
    	
        model.addAttribute("newCapitalFixo", new CapitalFixo());   	
        return "restricted/custo/CadastroTesteCapitalFixo";
    }
    
    @PostMapping("/cadastroTeste")
    public String salvar(@Valid CapitalFixo capitalFixo) {
   	
        capitalFixoService.salvar(capitalFixo);
        return "redirect:../capitalFixo";
    }
    
    @GetMapping("/alterar/{id}")
	public String alterar(@PathVariable Long id, Model model) {
		
    	Optional<CapitalFixo> capitalFixo = capitalFixoService.buscar(id);
    	capitalFixo.get().setId(id);
       
    	model.addAttribute("newCapitalFixo", capitalFixo);
    	
        return "restricted/custo/CadastroTesteCapitalFixo";
	}
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		
    	capitalFixoService.excluir(id);
		
    	return "redirect:../../capitalFixo";
	}
}
