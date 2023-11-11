package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.DepreciacaoLavouraCafe;
import com.epcafes.service.DepreciacaoLavouraCafeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/depreciacao/lavouraCafe")
public class DepreciacaoLavouraCafeController {
	
	@Autowired
	private DepreciacaoLavouraCafeService depreciacaoLavouraCafeService;
	
	@GetMapping
    public String listarDepreciacoesLavourasCafe(Model model) {
    	
        model.addAttribute("listaDepreciacoesLavourasCafe", depreciacaoLavouraCafeService.listarDepreciacoesLavourasCafe());
        model.addAttribute("newDeprecicaoLavouraCafe", new DepreciacaoLavouraCafe());
        
        return "restricted/custo/DepreciacaoLavouraCafe";
    }
	
	@PostMapping("/cadastro")
    public String salvar(@Valid DepreciacaoLavouraCafe depreciacaoLavouraCafe) {
    	    	
		depreciacaoLavouraCafe.setTenant_id(1L);
		
		depreciacaoLavouraCafeService.salvar(depreciacaoLavouraCafe);
        
        return "redirect:../depreciacao/lavouraCafe";
    }
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		
		depreciacaoLavouraCafeService.excluir(id);
		
    	return "redirect:../../depreciacao/lavouraCafe";
	}
    
    @GetMapping("/modal")
    public String modalDepreciacaoLavouraCafe(Model model, Optional<Long> id) {
    	
        DepreciacaoLavouraCafe depreciacaoLavouraCafe;
        
        if(id.isPresent()) 
        	depreciacaoLavouraCafe = depreciacaoLavouraCafeService.buscarPorId(id.get()).get();
        else 
        	depreciacaoLavouraCafe = new DepreciacaoLavouraCafe();
        
        model.addAttribute("depreciacaoLavouraCafe", depreciacaoLavouraCafe);             

        return "restricted/custo/ModalDepreciacaoLavouraCafe";
    }
}
