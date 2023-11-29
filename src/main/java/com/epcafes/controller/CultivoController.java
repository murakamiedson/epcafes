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
import com.epcafes.model.Cultivo;
import com.epcafes.model.Usuario;
import com.epcafes.service.CultivoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cultivo")
public class CultivoController {

    @Autowired
    private CultivoService cultivoService;
    
    @GetMapping
    public String listarCustosFixos(Model model,
    		@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	int currPage = page.orElse(1);
    	int pageSize = size.orElse(5);
        
        List<Cultivo> cultivos = cultivoService.listarCultivosPagined(currPage, pageSize);
        model.addAttribute("listaCultivos", cultivos);
        model.addAttribute("cultivo", new Cultivo());
        
        // Paginação 	        
        int qtdPaginas = (int) Math.ceil(cultivoService.listarCultivosPagined(currPage, pageSize).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        
        // Quantidade de itens por página
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        
        return "restricted/custo/Cultivo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid Cultivo cultivo) throws BusinessException {
    		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();  	    	

        cultivoService.salvar(cultivo);
        
        return "redirect:../cultivo";
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
    	cultivoService.excluir(id);
		
    	return "redirect:../../cultivo";
	}
    
    @GetMapping("/modal")
    public String modalCultivo(Model model, Optional<Long> id) throws BusinessException {
    	    	
    	Cultivo cultivo;
        
        if(id.isPresent()) 
        	cultivo = cultivoService.buscarPorId(id.get()).get();
        else 
        	cultivo = new Cultivo();
        
        model.addAttribute("cultivo", cultivo);
             
        return "restricted/custo/ModalCultivo";
    }
}