package com.epcafes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epcafes.exception.DespesaOutrosInsumoServicoException;
import com.epcafes.model.DespesaOutrosInsumoServico;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Usuario;
import com.epcafes.service.DespesaOutrosInsumoServicoService;
import com.epcafes.service.PropriedadeService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/despesa_insumo_servico")
@Log4j2
public class DespesaOutrosInsumoServicoController{

	@Autowired
	DespesaOutrosInsumoServicoService service;
	
	@Autowired
	PropriedadeService propService;

	@GetMapping
	public ModelAndView despesaInsumoServico(Authentication auth, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws DespesaOutrosInsumoServicoException{
		
		int currPage = page.orElse(1);    
		int pageSize = size.orElse(5);
		
		ModelAndView mev = new ModelAndView();
		
		Usuario userLogado = (Usuario) auth.getPrincipal();
		
		Propriedade p = propService.findByTenantId(userLogado.getTenant().getId()).get(0);
		
		if(p == null) {
			throw new DespesaOutrosInsumoServicoException("1337");
		}
		
		List<DespesaOutrosInsumoServico> despesas = service.findPaginated(currPage, pageSize);
		
		
		
		mev.setViewName("restricted/custo/DespesaOutrosInsumoServico");

		mev.addObject("despesas", despesas);
		
		// Paginação
        int qtdPaginas = (int) Math.ceil(service.findAll().size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        mev.addObject("pageNumbers", pageNumbers);
        mev.addObject("qtdPaginas", qtdPaginas);
        

        // Quantidade de itens por página
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);
        mev.addObject("qtdPorPaginaList", qtdPorPaginaList);
		
		return mev;
	}
	
	@PostMapping("/save")
	public ResponseEntity<String> create(DespesaOutrosInsumoServico data, Authentication auth) throws DespesaOutrosInsumoServicoException{
		Usuario user = (Usuario) auth.getPrincipal();
		
		if(data.getPropriedade() == null) {
			data.setPropriedade(user.getPropriedade());
			data.setTenantId(user.getTenant().getId());
			log.warn("Criado nova DespesaOutrosInsumoServico");
		}
		
		else {
			log.warn("Modificado DespesaOutrosInsumoServico");
		}

		DespesaOutrosInsumoServico novo = service.save(data);
	
		log.warn(novo);
		
		return ResponseEntity.status(202).body("OK");
		
		
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws DespesaOutrosInsumoServicoException{
		try{
			service.deleteById(id);
		log.warn("Deletado DespesaOutrosInsumoServico com id " + id.toString());
		} 
		catch (Exception e){
			return ResponseEntity.status(400).body("Não foi possível excluir essa despesa!\nTente novamente mais tarde!");
        }

        return ResponseEntity.status(202).body("Despesa deletada com sucesso!");
	}
	
	@GetMapping("/modal")
	public ModelAndView modal(Optional<Long> id) {
		ModelAndView mev = new ModelAndView();
		
		mev.setViewName("restricted/custo/ModalDespesaOutrosInsumoServico");
		
		DespesaOutrosInsumoServico desp;
		if(id.isPresent()) {
			desp = service.find(id.get());
		}
		else desp = new DespesaOutrosInsumoServico();
		
		mev.addObject("outraDespesa", desp);
		
		return mev;
		
	}
}
