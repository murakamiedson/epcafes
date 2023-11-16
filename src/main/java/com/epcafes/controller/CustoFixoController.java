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
import com.epcafes.model.CustoFixo;
import com.epcafes.model.Usuario;
import com.epcafes.service.CustoFixoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/custoFixo")
public class CustoFixoController {

    @Autowired
    private CustoFixoService custoFixoService;
    
    @GetMapping
    public String listarCustosFixos(Model model,
    		@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	int currPage = page.orElse(1);
    	int pageSize = size.orElse(5);
        
        List<CustoFixo> custosFixos = custoFixoService.listarCustosFixosPorPropriedadePagined(user.getPropriedade(), currPage, pageSize);
        model.addAttribute("listaCustosFixos", custosFixos);
        model.addAttribute("custoFixo", new CustoFixo());
        
        // Paginação 	        
        int qtdPaginas = (int) Math.ceil(custoFixoService.listarCustosFixosPorPropriedade(user.getPropriedade()).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        
        // Quantidade de itens por página
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        
        return "restricted/custo/CustoFixo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid CustoFixo custoFixo) throws BusinessException {
    		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	    	
    	custoFixo.setTenant_id(user.getTenant().getId());
    	custoFixo.setPropriedade(user.getPropriedade());

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
    	    	
        CustoFixo custoFixo;
        
        if(id.isPresent()) 
            custoFixo = custoFixoService.buscarPorId(id.get()).get();
        else 
            custoFixo = new CustoFixo();
        
        model.addAttribute("custoFixo", custoFixo);
             
        return "restricted/custo/ModalCustoFixo";
    }
}