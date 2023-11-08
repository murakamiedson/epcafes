package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.DepreciacaoMaquina;
import com.epcafes.service.DepreciacaoMaquinaService;
import com.epcafes.service.MaquinaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/depreciacao/maquina")
public class DepreciacaoMaquinaController {
	
	@Autowired
	private DepreciacaoMaquinaService depreciacaoMaquinaService;
	
	@Autowired
	private MaquinaService maquinaService;

	@GetMapping
    public String listarDepreciacoesMaquinas(Model model) {
    	
    	model.addAttribute("listaMaquinas", maquinaService.buscarMaquinas());
        model.addAttribute("listaDepreciacoesMaquinas", depreciacaoMaquinaService.listarDepreciacoesMaquinas());
        model.addAttribute("newDeprecicaoMaquina", new DepreciacaoMaquina());
        
        return "restricted/custo/DepreciacaoMaquina";
    }
	
	@PostMapping("/cadastro")
    public String salvar(@Valid DepreciacaoMaquina depreciacaoMaquina) {
    	    	
		depreciacaoMaquina.setTenant_id(1L);
		
		depreciacaoMaquinaService.salvar(depreciacaoMaquina);
        
        return "redirect:../depreciacao/maquina";
    }
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		
		depreciacaoMaquinaService.excluir(id);
		
    	return "redirect:../../depreciacao/maquina";
	}
    
    @GetMapping("/modal")
    public String modalDepreciacaoMaquina(Model model, Optional<Long> id) {
    	
        DepreciacaoMaquina depreciacaoMaquina;
        
        if(id.isPresent()) 
        	depreciacaoMaquina = depreciacaoMaquinaService.buscarPorId(id.get()).get();
        else 
        	depreciacaoMaquina = new DepreciacaoMaquina();
        
        model.addAttribute("depreciacaoMaquina", depreciacaoMaquina);             

        return "restricted/custo/ModalDepreciacaoMaquina";
    }
}
