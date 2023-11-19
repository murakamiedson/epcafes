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
import com.epcafes.model.TerraPropria;
import com.epcafes.model.Usuario;
import com.epcafes.service.TerraPropriaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/terraPropria")
public class TerraPropriaController {

    @Autowired
    private TerraPropriaService terraPropriaService;
    
    @GetMapping
    public String listarCustosFixos(Model model,
    		@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	int currPage = page.orElse(1);
    	int pageSize = size.orElse(5);
        
        List<TerraPropria> terras = terraPropriaService.listarTerraPropriaPagined(currPage, pageSize);
        model.addAttribute("listaTerras", terras);
        model.addAttribute("terraPropria", new TerraPropria());
        
        // Paginação 	        
        int qtdPaginas = (int) Math.ceil(terraPropriaService.listarTerraPropriaPagined(currPage, pageSize).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        
        // Quantidade de itens por página
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        
        return "restricted/custo/TerraPropria";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid TerraPropria terraPropria) throws BusinessException {
    		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();  	    	

        terraPropriaService.salvar(terraPropria);
        
        return "redirect:../terraPropria";
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
    	terraPropriaService.excluir(id);
		
    	return "redirect:../../terraPropria";
	}
    
    @GetMapping("/modal")
    public String modalTerraPropria(Model model, Optional<Long> id) throws BusinessException {
    	    	
    	TerraPropria terraPropria;
        
        if(id.isPresent()) 
        	terraPropria = terraPropriaService.buscarPorId(id.get()).get();
        else 
        	terraPropria = new TerraPropria();
        
        model.addAttribute("terraPropria", terraPropria);
             
        return "restricted/custo/ModalTerraPropria";
    }
}