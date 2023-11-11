package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.service.DepreciacaoInstalacaoService;
import com.epcafes.service.InstalacaoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/depreciacao/instalacao")
public class DepreciacaoInstalacaoController {
	
	@Autowired
	private DepreciacaoInstalacaoService depreciacaoInstalacaoService;
	
	@Autowired
	private InstalacaoService instalacaoService;
	
	@GetMapping
    public String listarDepreciacoesInstalacoes(Model model) {
    	
    	model.addAttribute("listaMaquinas", instalacaoService.findAll());
        model.addAttribute("listaDepreciacoesInstalacoes", depreciacaoInstalacaoService.listarDepreciacoesInstalacoes());
        model.addAttribute("newDeprecicaoInstalacao", new DepreciacaoInstalacao());
        
        return "restricted/custo/DepreciacaoInstalacao";
    }
	
	@PostMapping("/cadastro")
    public String salvar(@Valid DepreciacaoInstalacao depreciacaoInstalacao) {
    	    	
		depreciacaoInstalacao.setTenant_id(1L);
		
		depreciacaoInstalacaoService.salvar(depreciacaoInstalacao);
        
        return "redirect:../depreciacao/instalacao";
    }
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		
		depreciacaoInstalacaoService.excluir(id);
		
    	return "redirect:../../depreciacao/instalacao";
	}
    
    @GetMapping("/modal")
    public String modalDepreciacaoInstalacao(Model model, Optional<Long> id) {
    	
        DepreciacaoInstalacao depreciacaoInstalacao;
        
        if(id.isPresent()) 
        	depreciacaoInstalacao = depreciacaoInstalacaoService.buscarPorId(id.get()).get();
        else 
        	depreciacaoInstalacao = new DepreciacaoInstalacao();
        
        model.addAttribute("depreciacaoInstalacao", depreciacaoInstalacao);             

        return "restricted/custo/ModalDepreciacaoInstalacao";
    }
}
