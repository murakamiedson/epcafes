package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ModelAndView despesaInsumoServico(Authentication auth) throws DespesaOutrosInsumoServicoException{
		ModelAndView mev = new ModelAndView();
		
		Usuario userLogado = (Usuario) auth.getPrincipal();
		
		Propriedade p = propService.findByTenantId(userLogado.getTenant().getId()).get(0);
		
		if(p == null) {
			throw new DespesaOutrosInsumoServicoException("1337");
		}
		
		List<DespesaOutrosInsumoServico> despesas = service.getAll(p);
		
		mev.setViewName("restricted/custo/DespesaOutrosInsumoServico");

		mev.addObject("despesas", despesas);		
		
		return mev;
	}
	
	@PostMapping("/create")
	public void create(@RequestBody DespesaOutrosInsumoServico data, Authentication auth) throws DespesaOutrosInsumoServicoException{
		Usuario userLogado = (Usuario) auth.getPrincipal();
		data.setId(null);
		Propriedade p = propService.findByTenantId(userLogado.getTenant().getId()).get(0);
		
		if(p == null) {
			throw new DespesaOutrosInsumoServicoException("1337");
		}
		
		data.setPropriedade(p);
		data.setTenantId(userLogado.getTenant().getId()); 
		
		DespesaOutrosInsumoServico novo = service.save(data);
		log.warn("Criado nova DespesaOutrosInsumoServico");
		log.warn(novo);
	}
	
	@PostMapping("/delete/{id}")
	public void delete(@PathVariable Long id) throws DespesaOutrosInsumoServicoException{
		service.deleteById(id);
		log.warn("Deletado DespesaOutrosInsumoServico com id " + id.toString());
	}
	
	@PostMapping("/update")
	public void update(@RequestBody DespesaOutrosInsumoServico despesa_nova) throws DespesaOutrosInsumoServicoException{
		DespesaOutrosInsumoServico despesa = service.find(despesa_nova.getId());
		if(despesa == null) {
			throw new DespesaOutrosInsumoServicoException("413");
		}
		despesa.setDescricao(despesa_nova.getDescricao());
		despesa.setNotaFiscal(despesa_nova.getNotaFiscal());
		despesa.setValor(despesa_nova.getValor());
		despesa.setPorcUtilizacao(despesa_nova.getPorcUtilizacao());
		DespesaOutrosInsumoServico novo = service.save(despesa);
		log.warn("Atualizado DespesaOutrosInsumoServico");
		log.warn(novo);
	}
}
