package com.epcafes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.model.DespesaCustoFixo;
import com.epcafes.service.CustoFixoService;
import com.epcafes.service.DespesaCustoFixoService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/custoFixo/despesa")
public class DespesaCustoFixoController {
	
	@Autowired
	private DespesaCustoFixoService despesaCustoFixoService;
	
	@Autowired
	private CustoFixoService custoFixoService;
	
	@GetMapping
    public String listarDespesasCustosFixos(Model model) {
    	
		model.addAttribute("listaCustosFixos", custoFixoService.listarCustosFixos());
		
        model.addAttribute("listaDespesasCustosFixos", despesaCustoFixoService.listarDespesasCustosFixos());
        
        model.addAttribute("newDespesaCustoFixo", new DespesaCustoFixo());
        return "restricted/custo/DespesaCustoFixo";
    }
    
    @PostMapping("/cadastro")
    public String salvar(@Valid DespesaCustoFixo despesaCustoFixo) {
    	
    	log.info("entrei");
    	
    	despesaCustoFixo.setTenant_id(1L);
    	
    	log.info(despesaCustoFixo.getPorcentagemUtilizacao());
    	
    	despesaCustoFixoService.salvar(despesaCustoFixo);
    	
        return "redirect:../../custoFixo/despesa";
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		
    	despesaCustoFixoService.excluir(id);
		
    	return "redirect:../../despesa";
	}

    @GetMapping("/modal")
    public String modalDespesaCustoFixo(Model model, Optional<Long> id) {
    	
        DespesaCustoFixo despesaCustoFixo;
        
        if(id.isPresent()) 
            despesaCustoFixo = despesaCustoFixoService.buscarPorId(id.get()).get();
        else 
        	despesaCustoFixo = new DespesaCustoFixo();
        
        model.addAttribute("despesaCustoFixo", despesaCustoFixo);             

        return "restricted/custo/ModalDespesaCustoFixo";
    }
}
