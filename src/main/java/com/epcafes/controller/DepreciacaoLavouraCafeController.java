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
import com.epcafes.model.DepreciacaoLavouraCafe;
import com.epcafes.model.Usuario;
import com.epcafes.service.DepreciacaoLavouraCafeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/depreciacao/lavouraCafe")
public class DepreciacaoLavouraCafeController {
	
	@Autowired
	private DepreciacaoLavouraCafeService depreciacaoLavouraCafeService;
	
	@GetMapping
    public String listarDepreciacoesLavourasCafe(Model model, 
    		@RequestParam("page") Optional<Integer> page,    		
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        int currPage = page.orElse(1);
    	int pageSize = size.orElse(5);
    	
        model.addAttribute("listaDepreciacoesLavourasCafes", depreciacaoLavouraCafeService.listarDepreciacoesLavourasCafePorPropriedadePagined(user.getPropriedade(), currPage, pageSize));
        model.addAttribute("deprecicaoLavouraCafe", new DepreciacaoLavouraCafe());
        
        // Paginação
    	int qtdPaginas = (int) Math.ceil(depreciacaoLavouraCafeService.listarDepreciacoesLavourasCafePorPropriedade(user.getPropriedade()).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
		
        // Quantidade de itens por página
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        
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
