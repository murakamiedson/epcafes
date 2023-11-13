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
import com.epcafes.model.DepreciacaoLavouraCafe;
import com.epcafes.model.Usuario;
import com.epcafes.service.DepreciacaoLavouraCafeService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/depreciacao/lavouraCafe")
public class DepreciacaoLavouraCafeController {
	
	@Autowired
	private DepreciacaoLavouraCafeService depreciacaoLavouraCafeService;
	
	@GetMapping
    public String listarDepreciacoesLavourasCafe(Model model) throws BusinessException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
        model.addAttribute("listaDepreciacoesLavourasCafes", depreciacaoLavouraCafeService.listarDepreciacoesLavourasCafePorPropriedade(user.getPropriedade()));
        log.info(depreciacaoLavouraCafeService.listarDepreciacoesLavourasCafePorPropriedade(user.getPropriedade()));
        model.addAttribute("deprecicaoLavouraCafe", new DepreciacaoLavouraCafe());
        
        return "restricted/custo/DepreciacaoLavouraCafe";
    }
	
	@PostMapping("/cadastro")
    public String salvar(@Valid DepreciacaoLavouraCafe depreciacaoLavouraCafe) throws BusinessException {
    	    	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
		depreciacaoLavouraCafe.setTenant_id(user.getTenant().getId());
		depreciacaoLavouraCafe.setPropriedade(user.getPropriedade());
		
		depreciacaoLavouraCafeService.salvar(depreciacaoLavouraCafe);
        
        return "redirect:../../depreciacao/lavouraCafe";
    }
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
		depreciacaoLavouraCafeService.excluir(id);
		
    	return "redirect:../../lavouraCafe";
	}
    
    @GetMapping("/modal")
    public String modalDepreciacaoLavouraCafe(Model model, Optional<Long> id) throws BusinessException {
        
        DepreciacaoLavouraCafe depreciacaoLavouraCafe;
        
        if(id.isPresent()) 
        	depreciacaoLavouraCafe = depreciacaoLavouraCafeService.buscarPorId(id.get()).get();
        else 
        	depreciacaoLavouraCafe = new DepreciacaoLavouraCafe();
        
        model.addAttribute("depreciacaoLavouraCafe", depreciacaoLavouraCafe);             

        return "restricted/custo/ModalDepreciacaoLavouraCafe";
    }
}
