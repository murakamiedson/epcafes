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
import com.epcafes.model.DepreciacaoInstalacao;
import com.epcafes.model.Instalacao;
import com.epcafes.model.Usuario;
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
    public String listarDepreciacoesInstalacoes(Model model, 
    		@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
    	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        int currPage = page.orElse(1);
    	int pageSize = size.orElse(5);
        
    	List<Instalacao> instalacoes = instalacaoService.listarInstalacoesPorPropriedade(user.getPropriedade());
    	List<DepreciacaoInstalacao> depreciacoesInstalacoes = depreciacaoInstalacaoService.listarDepreciacoesInstalacoesPorPropriedadePagined(user.getPropriedade(), currPage, pageSize);
    	
    	model.addAttribute("listaInstalacoes", instalacoes);
        model.addAttribute("listaDepreciacoesInstalacoes", depreciacoesInstalacoes);
        model.addAttribute("deprecicaoInstalacao", new DepreciacaoInstalacao());
        
        int qtdPaginas = (int) Math.ceil(depreciacaoInstalacaoService.listarDepreciacoesInstalacoesPorPropriedade(user.getPropriedade()).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        
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
