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
import com.epcafes.model.DepreciacaoMaquina;
import com.epcafes.model.Maquina;
import com.epcafes.model.Usuario;
import com.epcafes.service.DepreciacaoMaquinaService;
import com.epcafes.service.MaquinaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/depreciacao/maquina")
public class DepreciacaoMaquinaController {
	
	@Autowired
	private DepreciacaoMaquinaService depreciacaoMaquinaService;
	
	@Autowired
	private MaquinaService maquinaService;

	@GetMapping
    public String listarDepreciacoesMaquinas(Model model, 
    		@RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) throws BusinessException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
    	
        int currPage = page.orElse(1);
    	int pageSize = size.orElse(5);
        
    	List<Maquina> maquinas = maquinaService.buscarPorTenant(user.getTenant().getId());
    	List<DepreciacaoMaquina> depreciacoesMaquinas = depreciacaoMaquinaService.listarDepreciacoesMaquinasPorTenantPagined(user.getTenant().getId(), currPage, pageSize);
    	
    	model.addAttribute("listaMaquinas", maquinas);
        model.addAttribute("listaDepreciacoesMaquinas", depreciacoesMaquinas);
        model.addAttribute("deprecicaoMaquina", new DepreciacaoMaquina());
                
    	int qtdPaginas = (int) Math.ceil(depreciacaoMaquinaService.listarDepreciacoesMaquinasPorTenant(user.getTenant().getId()).size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        
        return "restricted/custo/DepreciacaoMaquina";         
        
    }
	
	@PostMapping("/cadastro")
    public String salvar(@Valid DepreciacaoMaquina depreciacaoMaquina) throws BusinessException {
    	    	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
		depreciacaoMaquina.setTenant_id(user.getTenant().getId());
		
		depreciacaoMaquinaService.salvar(depreciacaoMaquina);
        
        return "redirect:../../depreciacao/maquina";
    }
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) throws BusinessException {
		
		depreciacaoMaquinaService.excluir(id);
		
    	return "redirect:../../maquina";
	}
    
    @GetMapping("/modal")
    public String modalDepreciacaoMaquina(Model model, Optional<Long> id) throws BusinessException {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        
        DepreciacaoMaquina depreciacaoMaquina;
        
        if(id.isPresent()) 
        	depreciacaoMaquina = depreciacaoMaquinaService.buscarPorId(id.get()).get();
        else 
        	depreciacaoMaquina = new DepreciacaoMaquina();
        
        model.addAttribute("depreciacaoMaquina", depreciacaoMaquina);
        
    	model.addAttribute("listaMaquinas", maquinaService.buscarPorTenant(user.getTenant().getId()));

        return "restricted/custo/ModalDepreciacaoMaquina";
    }
}