package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.service.DespesaCustoFixoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/custoFixo/despesa")
public class DespesaCustoFixoController {
	
	@Autowired
	private DespesaCustoFixoService despesaCustoFixoService;
	
	@GetMapping
    public String listarDespesasCustosFixos(Model model) {
    	
        model.addAttribute("listaCustosFixos", despesaCustoFixoService.listarDespesasCustosFixos());
        model.addAttribute("newDespesaCustoFixo", new DespesaCustoFixo());
        return "restricted/custo/DespesaCustoFixo";
    }
	
	@GetMapping("/cadastro")
    public String mostrar(Model model) {
    	
		model.addAttribute("newDespesaCustoFixo", new DespesaCustoFixo());
		return "restricted/custo/DespesaCustoFixo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid DespesaCustoFixo despesaCustoFixo) {
    	
    	despesaCustoFixo.setTenant_id(1L);
    	
    	despesaCustoFixoService.salvar(despesaCustoFixo);
    	
        return "redirect:../custoFixo/despesa";
    }
    
    @GetMapping("/alterar/{id}")
	public String alterar(@PathVariable Long id, Model model) {
		
    	Optional<DespesaCustoFixo> despesaCustoFixo = despesaCustoFixoService.buscarPorId(id);
    	despesaCustoFixo.get().setId(id);
       
    	model.addAttribute("newDespesaCustoFixo", despesaCustoFixo);
    	
    	return "restricted/custo/DespesaCustoFixo";
	}
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		
    	despesaCustoFixoService.excluir(id);
		
    	return "redirect:../../custoFixo/despesa";
	}
}
