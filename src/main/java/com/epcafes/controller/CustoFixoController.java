package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @PostMapping
    public String salvar(@Valid CustoFixo custoFixo) {
    	
    	custoFixo.setTenant_id(1L);
    	
    	Propriedade propriedadeTeste = new Propriedade();
    	propriedadeTeste.setId(1L);
    	propriedadeTeste.setNome("Teste");
    	
    	custoFixo.setPropriedade(propriedadeTeste);
        custoFixoService.salvar(custoFixo);
        return "redirect:custoFixo";
    }
    
    @GetMapping
    public String listarCustosFixos(Model model) {
    	
        model.addAttribute("listaCustosFixos", custoFixoService.listarCustosFixos());
        model.addAttribute("newCustoFixo", new CustoFixo());
        return "restricted/custos/custoFixo";
    } 
}
