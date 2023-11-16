package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Usuario;
import com.epcafes.service.DepreciacaoInstalacaoService;
import com.epcafes.service.InstalacaoService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/depreciacao/instalacao")
public class DepreciacaoInstalacaoController {
	
	@Autowired
	private DepreciacaoInstalacaoService depreciacaoInstalacaoService;
	
	@Autowired
	private InstalacaoService instalacaoService;
	
	@GetMapping
    public String listarDepreciacoesInstalacoes(Model model) throws BusinessException {
    	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
		log.info(instalacaoService.listarInstalacoesPorPropriedade(user.getPropriedade()));
    	model.addAttribute("listaInstalacoes", instalacaoService.listarInstalacoesPorPropriedade(user.getPropriedade()));
        model.addAttribute("listaDepreciacoesInstalacoes", depreciacaoInstalacaoService.listarDepreciacoesInstalacoesPorPropriedade(user.getPropriedade()));
        model.addAttribute("deprecicaoInstalacao", new DepreciacaoInstalacao());
        
        return "restricted/custo/DepreciacaoInstalacao";
    }
	
	@PostMapping("/cadastro")
    public String salvar(@Valid DepreciacaoInstalacao depreciacaoInstalacao) throws BusinessException {
    	    	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        depreciacaoInstalacao.setTenant_id(user.getTenant().getId());
        depreciacaoInstalacao.setPropriedade(user.getPropriedade());
		
		depreciacaoInstalacaoService.salvar(depreciacaoInstalacao);
        
        return "redirect:../../depreciacao/instalacao";
    }
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
		depreciacaoInstalacaoService.excluir(id);
		
    	return "redirect:../../instalacao";
	}
    
    @GetMapping("/modal")
    public String modalDepreciacaoInstalacao(Model model, Optional<Long> id) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        DepreciacaoInstalacao depreciacaoInstalacao;
        
        if(id.isPresent()) 
        	depreciacaoInstalacao = depreciacaoInstalacaoService.buscarPorId(id.get()).get();
        else 
        	depreciacaoInstalacao = new DepreciacaoInstalacao();
        
        model.addAttribute("depreciacaoInstalacao", depreciacaoInstalacao);             

    	model.addAttribute("listaInstalacoes", instalacaoService.listarInstalacoesPorPropriedade(user.getPropriedade()));
        
        return "restricted/custo/ModalDepreciacaoInstalacao";
    }
}
