package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.CustoFixo;
import com.epcafes.model.Propriedade;
import com.epcafes.service.CustoFixoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/custoFixo")
public class CustoFixoController {

    @Autowired
    private CustoFixoService custoFixoService;
    
    @GetMapping
    public String listarCustosFixos(Model model) {
    	
        model.addAttribute("listaCustosFixos", custoFixoService.listarCustosFixos());
        model.addAttribute("newCustoFixo", new CustoFixo());
        return "restricted/custo/CustoFixo";
    }
    
    @GetMapping("/cadastro")
    public String mostrar(Model model) {
    	
        model.addAttribute("newCustoFixo", new CustoFixo());
    	
        return "restricted/custo/CadastroCustoFixo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid CustoFixo custoFixo) {
    	
    	custoFixo.setTenant_id(1L);

    	Propriedade propriedadeTeste = new Propriedade();
    	propriedadeTeste.setId(1L);
    	propriedadeTeste.setNome("Teste");
    	
    	custoFixo.setPropriedade(propriedadeTeste);
        custoFixoService.salvar(custoFixo);
        return "redirect:../custoFixo";
    }
    
    @GetMapping("/alterar/{id}")
	public String alterar(@PathVariable Long id, Model model) {
		
    	Optional<CustoFixo> custoFixo = custoFixoService.buscar(id);
    	custoFixo.get().setId(id);
       
    	model.addAttribute("newCustoFixo", custoFixo);
    	
        return "restricted/custo/CadastroCustoFixo";
	}
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		
    	custoFixoService.excluir(id);
		
    	return "redirect:../../custoFixo";
	}
}
