package com.epcafes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epcafes.exception.BusinessException;
import com.epcafes.model.CapitalFixo;
import com.epcafes.model.CustoFixo;
import com.epcafes.model.Usuario;
import com.epcafes.service.CapitalFixoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/capitalFixo")
public class CapitalFixoController {

    @Autowired
    private CapitalFixoService capitalFixoService;
    
    @GetMapping
    public String listarCustosFixos(Model model,
    		@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	int currPage = page.orElse(1);
    	int pageSize = size.orElse(5);
        
        List<CapitalFixo> capitaisFixos = capitalFixoService.listarCapitaisFixosPagined(currPage, pageSize);
        model.addAttribute("listaCapitaisFixos", capitaisFixos);
        model.addAttribute("capitalFixo", new CapitalFixo());
        
        // Paginação 	        
        int qtdPaginas = (int) Math.ceil(capitalFixoService.listarCapitaisFixosPagined(currPage, pageSize).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        
        // Quantidade de itens por página
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        
        return "restricted/custo/CapitalFixo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid CapitalFixo capitalFixo) throws BusinessException {
    		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();  	    	

        capitalFixoService.salvar(capitalFixo);
        
        return "redirect:../capitalFixo";
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
    	capitalFixoService.excluir(id);
		
    	return "redirect:../../capitalFixo";
	}
    
    @GetMapping("/modal")
    public String modalCapitalFixo(Model model, Optional<Long> id) throws BusinessException {
    	    	
    	CapitalFixo capitalFixo;
        
        if(id.isPresent()) 
        	capitalFixo = capitalFixoService.buscarPorId(id.get()).get();
        else 
        	capitalFixo = new CapitalFixo();
        
        model.addAttribute("capitalFixo", capitalFixo);
             
        return "restricted/custo/ModalCapitalFixo";
    }
}