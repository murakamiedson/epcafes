
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
import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.model.Usuario;
import com.epcafes.service.CustoFixoService;
import com.epcafes.service.DespesaCustoFixoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/custoFixo/despesa")
public class DespesaCustoFixoController {
	
	@Autowired
	private DespesaCustoFixoService despesaCustoFixoService;
	
	@Autowired
	private CustoFixoService custoFixoService;
	
	@GetMapping
    public String listarDespesasCustosFixos(Model model,
    		@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        int currPage = page.orElse(1);
        int currSize = size.orElse(5);
        int pageSize = size.orElse(5);
        int qtdPorPaginaInt = qtdPorPagina.orElse(5);
        
        List<CustoFixo> custosFixos = custoFixoService.listarCustosFixosPorPropriedade(user.getPropriedade());
    	List<DespesaCustoFixo> despesasCustoFixo = despesaCustoFixoService.listarDespesasCustosFixosPorPropriedadePagined(user.getPropriedade(), currPage, pageSize);
        
    	int qtdPaginas = (int) Math.ceil(despesaCustoFixoService.listarDespesasCustosFixosPorPropriedade(user.getPropriedade()).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
    	
		model.addAttribute("listaCustosFixos", custosFixos);
        model.addAttribute("listaDespesasCustosFixos", despesasCustoFixo);
        model.addAttribute("newDespesaCustoFixo", new DespesaCustoFixo());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        model.addAttribute("currPage", currPage);
        model.addAttribute("qtdPorPagina", qtdPorPaginaInt);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        model.addAttribute("size", currSize);
        
        return "restricted/custo/DespesaCustoFixo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid DespesaCustoFixo despesaCustoFixo) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
    	despesaCustoFixo.setTenant_id(user.getTenant().getId());
    	
    	despesaCustoFixoService.salvar(despesaCustoFixo);
    	
        return "redirect:../../custoFixo/despesa";
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
    	despesaCustoFixoService.excluir(id);
		
    	return "redirect:../../despesa";
	}

    @GetMapping("/modal")
    public String modalDespesaCustoFixo(Model model, Optional<Long> id) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
        DespesaCustoFixo despesaCustoFixo;
        
        if(id.isPresent()) 
            despesaCustoFixo = despesaCustoFixoService.buscarPorId(id.get()).get();
        else 
        	despesaCustoFixo = new DespesaCustoFixo();
        
        model.addAttribute("despesaCustoFixo", despesaCustoFixo);
        
		model.addAttribute("listaCustosFixos", custoFixoService.listarCustosFixosPorPropriedade(user.getPropriedade()));

        return "restricted/custo/ModalDespesaCustoFixo";
    }
}
