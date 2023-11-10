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
import com.epcafes.model.CustoFixo;
import com.epcafes.model.Usuario;
import com.epcafes.service.CustoFixoService;
import com.epcafes.service.PropriedadeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/custoFixo")
public class CustoFixoController {

    @Autowired
    private CustoFixoService custoFixoService;
    
    @Autowired
    private PropriedadeService propriedadeService;
    
    @GetMapping
    public String listarCustosFixos(Model model) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	model.addAttribute("listaPropriedades", propriedadeService.findByTenantId(user.getTenant().getId()));
        model.addAttribute("listaCustosFixos", custoFixoService.listarCustosFixos(user.getTenant().getId()));
        model.addAttribute("custoFixo", new CustoFixo());
        return "restricted/custo/CustoFixo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid CustoFixo custoFixo) throws BusinessException {
    		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	    	
    	custoFixo.setTenant_id(user.getTenant().getId());

        custoFixoService.salvar(custoFixo);
        
        return "redirect:../custoFixo";
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
    	custoFixoService.excluir(id);
		
    	return "redirect:../../custoFixo";
	}
    
    @GetMapping("/modal")
    public String modalCustoFixo(Model model, Optional<Long> id) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	    	
        CustoFixo custoFixo;
        
        if(id.isPresent()) 
            custoFixo = custoFixoService.buscarPorId(id.get()).get();
        else 
            custoFixo = new CustoFixo();
        
        model.addAttribute("custoFixo", custoFixo);
        
    	model.addAttribute("listaPropriedades", propriedadeService.findByTenantId(user.getTenant().getId()));
        
        return "restricted/custo/ModalCustoFixo";
    }
}